package fr.istic.aco.Undo;

import java.util.ArrayList;
import java.util.List;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.utileFunctions.UtileFunctions;

public class UndoManagerImpl implements UndoManager {
	
	private List<CommandGlobal> command_history = new ArrayList<CommandGlobal>();
	private List<Memento> savedStates = new ArrayList<Memento>();
	
	private List<CommandGlobal> futur_command_history = new ArrayList<CommandGlobal>();
	private List<Memento> futur_savedStates = new ArrayList<Memento>();
	
	private int nbUndo = 0;
	

	@Override
	public void save(CommandGlobal command) {
		this.command_history.add(command);
		this.savedStates.add(command.sendMementoToCareTaker());
		this.futur_command_history.add(command);
		this.futur_savedStates.add(command.sendMementoToCareTaker());
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
			
			ArrayList<CommandGlobal> commandToIterate = UtileFunctions.deepCommandsArrayListCopy(command_history);
			List<Memento> statesToIterate = UtileFunctions.deepMementosArrayListCopy(savedStates);
			
			CommandGlobal command = null;
			
			for(int i=0; i < commandToIterate.size(); i++) {
				command = commandToIterate.get(i);
				
				if(statesToIterate.get(i) != null) {
					command.restoreFromMemento(statesToIterate.get(i));
				}
				
				command.execute();
			}
			
			this.nbUndo ++;
			
			//Check a second time because elements have been removed from lists
			if(command_history.size() > 0) {
				command_history.remove(command_history.size() - 1);
				savedStates.remove(savedStates.size() - 1);
				command_history.remove(command_history.size() - 1);
				savedStates.remove(savedStates.size() - 1);
			}
		}
	}
	
	@Override
	public void redo() {

		ArrayList<CommandGlobal> futureCommandToIterate = UtileFunctions.deepCommandsArrayListCopy(futur_command_history);
		List<Memento> futurStatesToIterate = UtileFunctions.deepMementosArrayListCopy(futur_savedStates);

		CommandGlobal command = null;


//		int nbRedoToDo = this.futur_command_history.size() - this.nbUndo;
		int nbRedoToDo = this.futur_command_history.size();

		for(int i=0; i < nbRedoToDo ; i++) {
			command = futureCommandToIterate.get(i);

			if(futurStatesToIterate.get(i) != null) {
				command.restoreFromMemento(futurStatesToIterate.get(i));
			}

			command.execute();

			this.nbUndo --;
			
			//Check a second because elements have been removed from lists
//			if(futur_command_history.size() > 0) {
//				futur_command_history.remove(futur_command_history.size() - 1);
//				futur_savedStates.remove(futur_savedStates.size() - 1);
//				futur_command_history.remove(futur_command_history.size() - 1);
//				futur_savedStates.remove(futur_savedStates.size() - 1);
//			}
		}
	}

}
