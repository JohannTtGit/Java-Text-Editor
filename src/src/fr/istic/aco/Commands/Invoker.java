package fr.istic.aco.Commands;

import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Exceptions.CommandHistoryException;

public interface Invoker {
	
	/**
	 * executes a given command
	 * @param command the command to be executed (non-null)
	 * @throws CommandException 
	 */
	void execute(Command command) throws CommandException;
	
	/**
	 * replays all the executed commands (in order from first to last executed)
	 * precondition: for every saved command there is a corresponding saved selection state
	 * @throws CommandHistoryException 
	 */
	void replay() throws CommandHistoryException;
	
	/**
	 * removes the last executed command
	 * precondition: history must at least contain 1 element (i.e. command)
	 * @throws CommandHistoryException 
	 */
	void undo() throws CommandHistoryException;
}
