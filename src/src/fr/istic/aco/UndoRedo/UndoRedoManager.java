package fr.istic.aco.UndoRedo;

import fr.istic.aco.Memento.CareTaker;

/**
 * Interface that provides the necessary methods as part of the UNDO features
 * In the context of the Memento design pattern features, embodies the CareTaker role
 */

public interface UndoRedoManager extends CareTaker {
	
	/**
	 * Undo a command by removing the last command and replaying from the
	 * beginning all the remaining.
	 */
	public void undo();
	
	/**
	 * Redo an undone command
	 */
	public void redo();
}