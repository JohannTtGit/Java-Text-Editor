package fr.istic.aco.Undo;

import java.util.ArrayList;
import java.util.List;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;

public class UndoManagerImpl implements UndoManager {
	
	private List<CommandGlobal> command_history = new ArrayList<CommandGlobal>();
	private List<Memento> savedStates = new ArrayList<Memento>();

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
		
		//Remove before copy otherwise two consecutive undo cannot work
		if(command_history.size() > 0) {
			
			command_history.remove(command_history.size() - 1);
			savedStates.remove(savedStates.size() - 1);
			
			ArrayList<CommandGlobal> commandToIterate = new ArrayList<>(command_history);
			List<Memento> statesToIterate = new ArrayList<>(savedStates);
			
			CommandGlobal command = null;
			
			for(int i=0; i < commandToIterate.size(); i++) {
				command = commandToIterate.get(i);
				command.restoreFromMemento(statesToIterate.get(i));
				command.execute();
			}
			
			//Check a second because elements have been removed from lists
			if(command_history.size() > 0) {
				command_history.remove(command_history.size() - 1);
				savedStates.remove(savedStates.size() - 1);
				command_history.remove(command_history.size() - 1);
				savedStates.remove(savedStates.size() - 1);
			}
		}
	}

}
