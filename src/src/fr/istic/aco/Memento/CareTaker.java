package fr.istic.aco.Memento;

import fr.istic.aco.Commands.CommandGlobal;

public interface CareTaker {
	public void save(CommandGlobal command);
	public void start();
	public void stop();
	public void replay();
	public MementoInsert getMemento(int index);
	
}
