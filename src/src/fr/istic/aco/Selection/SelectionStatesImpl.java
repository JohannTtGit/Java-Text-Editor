package fr.istic.aco.Selection;

import java.util.ArrayList;
import java.util.List;

import fr.istic.aco.Exceptions.CommandHistoryException;
import fr.istic.aco.Exceptions.SelectionStateException;

/**
 * implementation of the care taker that saves the selection states
 * @author Niklas, Johann
 *
 */
public class SelectionStatesImpl implements SelectionStates{

	private List<SelectionState> states = new ArrayList<SelectionState>();
	
	@Override
	public void add(SelectionState state) throws SelectionStateException {
		if(state == null) {
			throw new SelectionStateException("Invalid to add a null state");
		}
		
		states.add(state);
	}

	@Override
	public SelectionState getState(int index) throws SelectionStateException {
		if(index > states.size()) {
			throw new SelectionStateException("Index out of range");
		}
		
		return states.get(index);
	}

	@Override
	public void removeLast() throws CommandHistoryException {
		if(states == null) {
			throw new CommandHistoryException("Empty state history, cannot remove the last element");
		}
		states.remove(states.size() - 1);	
	}

	@Override
	public int getSize() {
		return states.size();
	}

}
