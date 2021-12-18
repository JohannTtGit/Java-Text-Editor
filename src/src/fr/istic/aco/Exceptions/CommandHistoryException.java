package fr.istic.aco.Exceptions;

/**
 * Exception dedicated to the stored commands as part of the Memento design pattern
 * @author Johann
 */
public class CommandHistoryException extends Exception {

	public CommandHistoryException() {};
	
	public CommandHistoryException(String message) {
		super(message);
	}
}
