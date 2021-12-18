package fr.istic.aco.Memento;

/**
 * Memento interface implementation, dedicated to the concrete commands "setBeginIndexCommand" and "setEndIndexCommand".
 */
public class MementoIndex implements Memento {
	
	private int savedIndex;
	
	public MementoIndex(int index) {
		this.savedIndex = index;
	}

	@Override
	public String getState() {
		return "";
	}

	@Override
	public int getIntState() {
		return this.savedIndex;
	}
	
	
}
