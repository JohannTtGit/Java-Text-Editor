package fr.istic.aco.Memento;

import fr.istic.aco.Commands.CommandGlobal;


/*
 * As part of the Memento design pattern, it is obviously a classical CareTaker.
 * As part of the Command design pattern, it is an Invoker and a Receiver while executing ReplayCommand.
 */
public interface CareTaker {
	
	/*
	 * Save a command in the history & add the corresponding command state (Memento) in the Memento history
	 */
	public void save(CommandGlobal command);
	
	/*
	 * Switch the record boolean value to true
	 */
	public void start();
	
	/*
	 * Switch the record boolean value to false 
	 */
	public void stop();
	
	/*
	 * Replay all the commands saved between start & stop.
	 * The buffer is not erased, replay() adds content after the pre-existing content. 
	 */
	public void replay();
	
	/*
	 * @return : Memento state 
	 */
	public Memento getMemento(int index);
	
	/*
	 * Undo a command by removing the last command and replaying from the
	 * beginning all the remaining.
	 */
	public void undo();
	
}
