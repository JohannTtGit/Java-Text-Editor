package fr.istic.aco.Selection;

/**
 * @author Niklas, Johann
 * The Originator of the memento desing pattern
 * Implements the interface SelectionState
 */
public class SelectionStateImpl implements SelectionState {

	private int beginIndex = 0;
	private int endIndex = 0;
	
	public SelectionStateImpl() {}
	
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

	@Override
	public void setBeginIndex(int beginIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEndIndex(int endIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreFromMemento() {
		// TODO Auto-generated method stub
		
	}

}
