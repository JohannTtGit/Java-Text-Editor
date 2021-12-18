package fr.istic.aco.Commands;

import fr.istic.aco.Exceptions.CommandException;

/**
 * Provides Invoker role functions in the Command Design Pattern context
 * @author Johann, Niklas
 *
 */
public interface Invoker {
	
	void addCommandToInvoker(String commandName, Command command);
	
	/**
	 * executes a given command
	 * @param commandName : command's name to be executed (non-null)
	 * @throws CommandException
	 */
	void play(String commandName) throws CommandException;

	
	//Method to handle the function's argument in the context of the Command design pattern
	
	/**
	 * Return the stored content to insert, used by InsertCommand
	 * @return Stored content to insert with method insert()
	 */
	String getContentToInsert();
	
	/**
	 * Update the stored content to insert (normally before executing each command)
	 */
	void setContentToInsert(String content);
	
	/**
	 * Update the stored content to insert, used by InsertCommand
	 * @return Stored Selection begin index 
	 */
	int getBeginIndex();
	
	/**
	 * Return the selection begin index
	 * @return Stored Selection end index 
	 */
	int getEndIndex();
	
	/**
	 * Return the selection end index
	 * Update the stored Selection begin index
	 */
	void setBeginIndex(int beginIndex);
	
	/**
	 * Update the stored Selection end index
	 */
	void setEndIndex(int endIndex);
}
