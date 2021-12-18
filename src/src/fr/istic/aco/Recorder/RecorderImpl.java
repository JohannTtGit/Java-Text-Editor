package fr.istic.aco.Recorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;

public class RecorderImpl implements Recorder {
	
	private List<CommandGlobal> command_history = new ArrayList<>();
	List<Memento> savedStates = new ArrayList<>();
	
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
		List<CommandGlobal> command_to_iterate = new ArrayList<>();
		List<Memento> memento_to_iterate = new ArrayList<>();
		
		Iterator<CommandGlobal> iterCommand = command_history.iterator();
		while(iterCommand.hasNext()) {
			command_to_iterate.add(iterCommand.next());
		}
		
		Iterator<Memento> iterStates = savedStates.iterator();
		while(iterStates.hasNext()) {
			memento_to_iterate.add(iterStates.next());
		}
		
//		Collections.reverse(command_to_iterate);
//		Collections.reverse(memento_to_iterate);
		
		for(int i=0; i < command_to_iterate.size(); i++) {
			command = command_history.get(i);
			
			if(memento_to_iterate.get(i) != null) {
				command.restoreFromMemento(memento_to_iterate.get(i));
			}
			
			command.execute();
		}	
	}
	
}
