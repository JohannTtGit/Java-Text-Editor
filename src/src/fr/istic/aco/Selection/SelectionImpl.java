package fr.istic.aco.Selection;

/**
 * implements the interface Selection that allows to get and set user's selection
 * @author Niklas, Johann
 *
 */
public class SelectionImpl implements Selection {
	
	int beginIndex; // current selection for begin Index
	int endIndex; // current selection for end Index
	final int BUFFER_BEGIN_INDEX = 0;
	
	StringBuilder buffer;
	
	public SelectionImpl(StringBuilder buffer) {
		this.buffer = buffer;
		setBeginIndex(0);
		setEndIndex(0);
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
	public int getBufferBeginIndex() {
		return BUFFER_BEGIN_INDEX;
	}
	
	@Override
	public int getBufferEndIndex() {
		return buffer.length();
	}
	
	@Override
	public void setBeginIndex(int beginIndex) {
		if(beginIndex > getBufferEndIndex()) {
			throw new IndexOutOfBoundsException();
		}
		this.beginIndex = beginIndex;
		
	}
	
	@Override
	public void setEndIndex(int endIndex) throws IndexOutOfBoundsException {
		if(endIndex > this.getBufferEndIndex()) {
			throw new IndexOutOfBoundsException("End selection index exceed buffer length");
		}
		this.endIndex = endIndex;
	}
	
}
