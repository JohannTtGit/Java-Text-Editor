package fr.istic.aco.Recorder;

import fr.istic.aco.Memento.CareTaker;

/**
 * As part of the Record features, embodies the CareTaker role.
 * As part of the Command design pattern, it is an Invoker and a Receiver while executing ReplayCommand.
 */
public interface Recorder extends CareTaker {
	
	/**
	 * Switch the record boolean value to true
	 */
	public void start();
	
	/**
	 * Switch the record boolean value to false 
	 */
	public void stop();
	
	/**
	 * Replay all the commands saved between start and stop.
	 * The buffer is not erased, replay() adds content after the pre-existing content. 
	 */
	public void replay();
	
}
