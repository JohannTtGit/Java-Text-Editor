package fr.istic.aco.Selection;

/**
 * @author Niklas, Johann
 * The Originator of the memento desing pattern
 * Implements the interface SelectionState
 */
public class SelectionStateImpl implements SelectionState {

	private final int beginIndex;
	private final int endIndex;
	
	public SelectionStateImpl(int beginIndex, int endIndex) {
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}
	
	@Override
	public int getBeginIndex() {
		return beginIndex;
	}

	@Override
	public int getEndIndex() {
		return endIndex;
	}

}
