package fr.istic.aco.Selection;
/**
 * interface for the selection memento
 * @author Niklas, Johann
 *
 */
public interface SelectionState {
	/**
	 * 
	 * @return the begin index of the saved selection state
	 */
	int getBeginIndex();
	
	/**
	 * 
	 * @return the end index of the saved selection state
	 */
	int getEndIndex();
}
