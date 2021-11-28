package fr.istic.aco.Commands;

import fr.istic.aco.Exceptions.CommandException;

public interface Invoker {
	
	void addCommandToInvoker(String commandName, Command command);
	
	/**
	 * executes a given command
	 * @param command's name to be executed (non-null)
	 * @throws CommandException
	 */
	void play(String commandName) throws CommandException;

	
	//Method to handle the function's argument in the context of the Command design pattern
	String getContentToInsert();
	void setContentToInsert(String content);
	
	int getBeginIndex();
	int getEndIndex();
	void setBeginIndex(int beginIndex);
	void setEndIndex(int endIndex);
}
