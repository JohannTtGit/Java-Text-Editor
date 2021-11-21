package fr.istic.aco.Selection;

import java.util.ArrayList;
import java.util.List;

/**
 * implementation of the care taker that saves the selection states
 * @author Niklas, Johann
 *
 */
public class SelectionStatesImpl implements SelectionStates{

	private List<SelectionState> states = new ArrayList<SelectionState>();
	
	@Override
	public void add(SelectionState state) {
		assert null != state: "Invalid to add a null state";
		states.add(state);
	}

	@Override
	public SelectionState getState(int index) {
		assert index < states.size(): "The given index is out of range";
		return states.get(index);
	}

	@Override
	public void removeLast() {
		assert null != states: "No state history, so you cannot remove the last element";
		states.remove(states.size() - 1);	
	}

	@Override
	public int getSize() {
		return states.size();
	}

}
