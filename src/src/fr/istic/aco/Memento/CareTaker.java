package fr.istic.aco.Memento;

import fr.istic.aco.Commands.CommandGlobal;


/**
 * CareTaker interface as part of the Memento design pattern.
 */
public interface CareTaker {
	
	/**
	 * Save a command in the history and add the corresponding command state (Memento) in the Memento history
	 * @param command Command to be stored in the history
	 */
	public void save(CommandGlobal command);
	
	
	/**
	 * Return the Memento state
	 * @param index Memento index to be returned
	 * @return Memento state 
	 */
	public Memento getMemento(int index);
}
