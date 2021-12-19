package fr.istic.aco.Recorder;

import java.util.ArrayList;
import java.util.List;
import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.utileFunctions.UtileFunctions;

public class RecorderImpl implements Recorder {
	
	private List<CommandGlobal> command_history = new ArrayList<>();
	private List<Memento> savedStates = new ArrayList<>();
	
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
		//Execute a deep copy
		List<CommandGlobal> command_to_iterate = UtileFunctions.deepCommandsArrayListCopy(command_history);
		List<Memento> memento_to_iterate = UtileFunctions.deepMementosArrayListCopy(savedStates);
		
		for(int i=0; i < command_to_iterate.size(); i++) {
			command = command_history.get(i);
			
			if(memento_to_iterate.get(i) != null) {
				command.restoreFromMemento(memento_to_iterate.get(i));
			}
			
			command.execute();
		}	
	}
	
}
