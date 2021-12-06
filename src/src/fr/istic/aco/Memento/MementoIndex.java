package fr.istic.aco.Memento;

public class MementoIndex implements Memento {
	
	private int index;
	
	public MementoIndex(int index) {
		this.index = index;
	}

	@Override
	public String getState() {
		return "";
	}

	@Override
	public int getIntState() {
		return this.index;
	}
	
	
}
