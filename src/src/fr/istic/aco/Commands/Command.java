package fr.istic.aco.Commands;

import fr.istic.aco.Memento.Originator;

/**
 * Command Interface
 * @author Niklas, Johann
 *
 */
public interface Command extends Originator {
	/**
	 * executes a command on the receiver
	 */
	public void execute();
}
