package fr.istic.aco.Undo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;

public class UndoManagerImpl implements UndoManager {
	
	private List<CommandGlobal> command_history = new ArrayList<CommandGlobal>();
	List<Memento> savedStates = new ArrayList<Memento>();

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
		ArrayList<CommandGlobal> commandToIterate = new ArrayList<>(command_history);
		List<Memento> statesToIterate = new ArrayList<>(savedStates);
		
		commandToIterate.remove(commandToIterate.size() - 1);
		statesToIterate.remove(statesToIterate.size() - 1);
		
		Collections.reverse(commandToIterate);
		Collections.reverse(statesToIterate);
		
		CommandGlobal command = null;
		
		for(int i=0; i < commandToIterate.size(); i++) {
			command = commandToIterate.get(i);
			command.restoreFromMemento(statesToIterate.get(i));
			command.execute();
		}
	}

}
