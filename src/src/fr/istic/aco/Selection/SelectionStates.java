package fr.istic.aco.Selection;

import fr.istic.aco.Exceptions.CommandHistoryException;
import fr.istic.aco.Exceptions.SelectionStateException;

/**
 * the interface of the care taker that saves all the selection states
 * @author Niklas, Johann
 *
 */
public interface SelectionStates {
	
	/**
	 * 
	 * @param state state to be saved in the care taker (non-null)
	 * @throws SelectionStateException 
	 */
	void add(SelectionState state) throws SelectionStateException;
	
	/**
	 * 
	 * @param index position in the collection from which the state should be drawn from
	 * @return the state at the given position in the collection
	 * precondition: index must be within the collection size - 1
	 * @throws SelectionStateException 
	 */
	SelectionState getState(int index) throws SelectionStateException;
	
	/**
	 * removes the last saved state from the care taker
	 * precondition: care taker collection non-null
	 * @throws CommandHistoryException 
	 */
	void removeLast() throws CommandHistoryException;
	
	/**
	 * 
	 * @return the size of the collection (i.e. number of saved states)
	 */
	int getSize();
}
