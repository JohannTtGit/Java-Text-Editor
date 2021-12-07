package fr.istic.aco.Commands;

import fr.istic.aco.Exceptions.CommandException;

public interface Invoker {
	
	void addCommandToInvoker(String commandName, Command command);
	
	/**
	 * executes a given command
	 * @param commandName : command's name to be executed (non-null)
	 * @throws CommandException
	 */
	void play(String commandName) throws CommandException;

	
	//Method to handle the function's argument in the context of the Command design pattern
	
	/*
	 * @return Stored content to insert with method insert()
	 */
	String getContentToInsert();
	
	/*
	 * Update the stored content to insert (normally before executing each command)
	 */
	void setContentToInsert(String content);
	
	/*
	 * @return Stored Selection begin index 
	 */
	int getBeginIndex();
	
	/*
	 * @return Stored Selection end index 
	 */
	int getEndIndex();
	
	/*
	 * Update the stored Selection begin index
	 */
	void setBeginIndex(int beginIndex);
	
	/*
	 * Update the stored Selection end index
	 */
	void setEndIndex(int endIndex);
}
