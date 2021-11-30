package fr.istic.aco.Memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
	
	List<Memento> savedStates = new ArrayList<Memento>();
	
	public void addMemento(Memento memento) {
		savedStates.add(memento);
	}
	
	public Memento getMemento(int index) {
		return this.savedStates.get(index);
	}
}
