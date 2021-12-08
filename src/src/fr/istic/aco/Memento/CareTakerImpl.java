package fr.istic.aco.Memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import fr.istic.aco.Commands.CommandGlobal;

public class CareTakerImpl implements CareTaker {
	
	private List<CommandGlobal> command_history = new ArrayList<CommandGlobal>();
	List<Memento> savedStates = new ArrayList<Memento>();
	private boolean record = false;
	
	@Override
	public void save(CommandGlobal command){
		if(record) {
			this.command_history.add(command);
			this.savedStates.add(command.sendMementoToCareTaker());
		}
	}
	
	@Override
	public Memento getMemento(int index) {
		return this.savedStates.get(index);
	}

	@Override
	public void start() {
		this.record = true;
	}

	@Override
	public void stop() {
		this.record = false;
	}

	@Override
	public void replay() {
		
		CommandGlobal command = null;
		
		//Copy before looping, because execute() add the restored command to the history -> Infinite loop
		List<CommandGlobal> list_to_iterate = new ArrayList<>(command_history);
		
		for(int i=0; i < list_to_iterate.size(); i++) {
			command = command_history.get(i);
			command.restoreFromMemento(savedStates.get(i));
			command.execute();
		}	
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
