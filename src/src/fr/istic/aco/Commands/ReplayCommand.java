package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;

public class ReplayCommand implements Command {
	
	private CareTaker caretaker; //Needed as part of the Memento design pattern
	
	public ReplayCommand(CareTaker caretaker) {
		this.caretaker = caretaker;
	}

	@Override
	public void execute() {
		caretaker.replay();
	}
}
