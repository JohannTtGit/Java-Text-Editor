package fr.istic.aco.Undo;

import fr.istic.aco.Memento.CareTaker;

/*
 * In the context of UNDO features, embodies the CareTaker role
 * 
 */

public interface UndoManager extends CareTaker {
	
	/*
	 * Undo a command by removing the last command and replaying from the
	 * beginning all the remaining.
	 */
	public void undo();
}