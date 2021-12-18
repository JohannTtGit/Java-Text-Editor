package fr.istic.aco.Memento;

import fr.istic.aco.Commands.CommandGlobal;


/**
 * CareTaker interface as part of the Memento design pattern.
 */
public interface CareTaker {
	
	/**
	 * Save a command in the history and add the corresponding command state (Memento) in the Memento history
	 */
	public void save(CommandGlobal command);
	
	
	/**
	 * Return the Memento state
	 * @return : Memento state 
	 */
	public Memento getMemento(int index);
}
