package fr.istic.aco.Commands;

import java.util.ArrayList;
import java.util.List;

import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Exceptions.CommandHistoryException;
import fr.istic.aco.Exceptions.SelectionStateException;
import fr.istic.aco.Selection.SelectionState;
import fr.istic.aco.Selection.SelectionStateImpl;
import fr.istic.aco.Selection.SelectionStates;
import fr.istic.aco.Selection.SelectionStatesImpl;
import fr.istic.aco.editor.Engine;

/**
 * @author Niklas, Johann 
 */
public class InvokerImpl implements Invoker {
	private List<Command> command_history = new ArrayList<Command>();
	
	//Attributes used to ensure the Command design pattern
	private String contentToInsert; //Used for insertion in the context of Command design pattern
	private int beginIndex; //Used for setBeginIndexCommand
	private int endIndex; //Used for setEndIndexCommand
	
	
	@Override
	public void play(Command command) throws CommandException {
		
		if(command == null) {
			throw new CommandException("You cannot execute a null command");
		}

		this.command_history.add(command);
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
