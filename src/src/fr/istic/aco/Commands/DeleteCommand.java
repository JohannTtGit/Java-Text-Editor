package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.editor.Engine;

/**
 * Implements the delete operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class DeleteCommand implements CommandGlobal {

	private Engine engine;
	private CareTaker caretaker;
	private UndoManager undoManager;
	
	public DeleteCommand(Engine engine, CareTaker caretaker, UndoManager undoManager) {
		this.engine = engine;
		this.caretaker = caretaker;
		this.undoManager = undoManager;
	}
	
	@Override
	public void execute() {
		engine.delete();
		caretaker.save(this);
		undoManager.save(this);
	}

	@Override
	public void restoreFromMemento(Memento memento) {}

	@Override
	public Memento sendMementoToCareTaker() {
		return null;
	}
	
}
