package fr.istic.aco.Exceptions;

/**
 * Exception dedicated to the Command design pattern commands
 * @author Johann
 */
public class CommandException extends Exception {
	
	public CommandException() {};
	
	public CommandException(String message) {
		super(message);
	}
}
