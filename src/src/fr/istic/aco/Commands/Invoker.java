package fr.istic.aco.Commands;

import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Exceptions.CommandHistoryException;
import fr.istic.aco.Exceptions.SelectionStateException;

public interface Invoker {
	
	/**
	 * executes a given command
	 * @param command the command to be executed (non-null)
	 * @throws CommandException 
	 * @throws SelectionStateException 
	 */
	void play(Command command) throws CommandException, SelectionStateException;
	
	/**
	 * replays all the executed commands (in order from first to last executed)
	 * precondition: for every saved command there is a corresponding saved selection state
	 * @throws CommandHistoryException 
	 * @throws SelectionStateException 
	 */
	void replay() throws CommandHistoryException, SelectionStateException;
	
	/**
	 * removes the last executed command
	 * precondition: history must at least contain 1 element (i.e. command)
	 * @throws CommandHistoryException 
	 * @throws SelectionStateException 
	 */
	void undo() throws CommandHistoryException, SelectionStateException;
}
