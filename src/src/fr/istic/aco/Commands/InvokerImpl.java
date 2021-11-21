package fr.istic.aco.Commands;

import java.util.ArrayList;
import java.util.List;

import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Exceptions.CommandHistoryException;
import fr.istic.aco.Selection.SelectionState;
import fr.istic.aco.Selection.SelectionStateImpl;
import fr.istic.aco.Selection.SelectionStates;
import fr.istic.aco.Selection.SelectionStatesImpl;
import fr.istic.aco.editor.Engine;

/**
 * @author Niklas, Johann
 * Implements the invoker who servers a singelton to execute commands (command design pattern), ensure book keeping (command design pattern) and saves states as originator (cf memento pattern) 
 */
public class InvokerImpl implements Invoker {
	private List<Command> command_history = new ArrayList<Command>();
	private Engine engine;
	private SelectionStates states = new SelectionStatesImpl();
	
	
	public InvokerImpl(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void execute(Command command) throws CommandException {
		
		if(command == null) {
			throw new CommandException("You cannot execute a null command");
		}

		this.command_history.add(command);
		states.add(new SelectionStateImpl(engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
		command.execute();
	}
	
	@Override
	public void replay() throws CommandHistoryException {
		
		if(command_history.size() != states.getSize()) {
			throw new CommandHistoryException("Size of states and command history are not equal. Ensure the commands are executed through the invoker only");
		}
		
		engine.clearBuffer();
		
		for(int i = 0; i < command_history.size(); i++) {
			//restore selection state from the memento to execute command with correct selection
			SelectionState state = states.getState(i);
			engine.getSelection().setEndIndex(state.getEndIndex());
			engine.getSelection().setBeginIndex(state.getBeginIndex());
			command_history.get(i).execute();
		}
	}
	
	@Override
	public void undo() throws CommandHistoryException {
		
		if(command_history.size() < 1) {
			throw new CommandHistoryException("No command history. You cannot undo any command");
		}
		
		command_history.remove(command_history.size() - 1);
		states.removeLast();
		replay();
	}

}
