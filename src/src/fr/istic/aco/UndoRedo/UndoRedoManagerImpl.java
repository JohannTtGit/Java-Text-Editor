package fr.istic.aco.UndoRedo;

import java.util.ArrayList;
import java.util.List;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.utileFunctions.UtileFunctions;

public class UndoRedoManagerImpl implements UndoRedoManager {
	
	private List<CommandGlobal> command_history = new ArrayList<CommandGlobal>();
	private List<Memento> savedStates = new ArrayList<Memento>();
	
	private List<CommandGlobal> futur_command_history = new ArrayList<CommandGlobal>();
	private List<Memento> futur_savedStates = new ArrayList<Memento>();
	

	@Override
	public void save(CommandGlobal command) {
		this.command_history.add(command);
		this.savedStates.add(command.sendMementoToCareTaker());
	}

	@Override
	public Memento getMemento(int index) {
		return this.savedStates.get(index);
	}
	
	@Override
	public void undo() {
		
		if(command_history.size() > 0) {
			
			//Add the command and state to be removed to the "future" lists, to enable the redo function
			futur_command_history.add(command_history.get(command_history.size() -1));
			futur_savedStates.add(savedStates.get(savedStates.size() - 1));

			//Remove before copy otherwise two consecutive undo cannot work
			command_history.remove(command_history.size() - 1);
			savedStates.remove(savedStates.size() - 1);
			
			//Deep copy before looping, because execute() add the restored command to the history when replaying -> Infinite loop
			ArrayList<CommandGlobal> commandToIterate = UtileFunctions.deepCommandsArrayListCopy(command_history);
			List<Memento> statesToIterate = UtileFunctions.deepMementosArrayListCopy(savedStates);
			
			CommandGlobal command = null;
			
			//Get the nb of command in history before replaying to remove
			//the added commands during the replaying loop
			int nbCommandToReplay = command_history.size() - 1;
			
			for(int i=0; i < commandToIterate.size(); i++) {
				command = commandToIterate.get(i);
				
				if(statesToIterate.get(i) != null) {
					command.restoreFromMemento(statesToIterate.get(i));
				}

				command.execute();
			}

			//Remove commands added in history during the replay
			if(command_history.size() > 0) {
				for(int i=nbCommandToReplay; i < command_history.size(); i++) {
					command_history.remove(i);
					savedStates.remove(i);
				}
			}
		}
	}
	
	@Override
	public void redo() {
		
		if(this.futur_command_history.size() > 0) {
			CommandGlobal command = futur_command_history.get(futur_command_history.size() - 1);

			if(futur_command_history.get(futur_command_history.size() - 1) != null) {
				command.restoreFromMemento(futur_savedStates.get(futur_savedStates.size() - 1));
			}

			command.execute();

			futur_command_history.remove(futur_command_history.size() - 1);
			futur_savedStates.remove(futur_savedStates.size() - 1);
		}
	}
}
