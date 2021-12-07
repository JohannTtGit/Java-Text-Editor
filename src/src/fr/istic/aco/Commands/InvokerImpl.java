package fr.istic.aco.Commands;

import java.util.HashMap;
import java.util.Map;

import fr.istic.aco.Exceptions.CommandException;

/**
 * @author Niklas, Johann 
 */
public class InvokerImpl implements Invoker {
	private Map<String, Command> commands = new HashMap<String, Command>();
	
	//Attributes used to ensure the Command design pattern
	private String contentToInsert; //Used for insertion in the context of Command design pattern
	private int beginIndex; //Used for setBeginIndexCommand
	private int endIndex; //Used for setEndIndexCommand
	
	
	@Override
	public void addCommandToInvoker(String commandName, Command command) {
		this.commands.put(commandName, command);
	}
	
	@Override
	public void play(String nameCommand) throws CommandException {
		
		if(commands.get(nameCommand) == null) {
			throw new CommandException("Unknown Command");
		}
		
		Command command = commands.get(nameCommand);
		command.execute();
	}
	
	
	@Override
	public String getContentToInsert() {
		return this.contentToInsert;
	}
	
	@Override
	public void setContentToInsert(String content) {
		this.contentToInsert = content;
	}

	@Override
	public int getBeginIndex() {
		return this.beginIndex;
	}

	@Override
	public int getEndIndex() {
		return this.endIndex;
	}

	@Override
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	@Override
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;	
	}

}
