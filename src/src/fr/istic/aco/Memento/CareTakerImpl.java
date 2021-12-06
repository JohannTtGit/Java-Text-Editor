package fr.istic.aco.Memento;

import java.util.ArrayList;
import java.util.List;

import fr.istic.aco.Commands.CommandGlobal;

public class CareTakerImpl implements CareTaker {
	
	private List<CommandGlobal> command_history = new ArrayList<CommandGlobal>();
	List<Memento> savedStates = new ArrayList<Memento>();
	
	@Override
	public void save(CommandGlobal command){
		this.command_history.add(command);
		this.savedStates.add(command.sendMementoToCareTaker());
	}
	
	@Override
	public Memento getMemento(int index) {
		return this.savedStates.get(index);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replay() {
		
		CommandGlobal command = null;
		
		//Faire une copie avant car execute ajoute aussi la commande a l'historique donc créer une boucle infinie
		List<CommandGlobal> list_to_iterate = new ArrayList<>(command_history);
		
		for(int i=0; i < list_to_iterate.size(); i++) {
			command = command_history.get(i);
			command.restoreFromMemento(savedStates.get(i));
			command.execute();
		}
		
	}
}
