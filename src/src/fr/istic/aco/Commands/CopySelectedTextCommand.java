package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.UndoRedo.UndoRedoManager;
import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete copy selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class CopySelectedTextCommand implements CommandGlobal {
	
	private Engine engine;
	private CareTaker caretaker;
	private UndoRedoManager undoManager;
	
	public CopySelectedTextCommand(Engine engine, CareTaker caretaker, UndoRedoManager undoManager) {
		this.engine = engine;
		this.caretaker = caretaker;
		this.undoManager = undoManager;
	}

	@Override
	public void execute() {
		caretaker.save(this);
		undoManager.save(this);
		engine.copySelectedText();
	}


	@Override
	public Memento sendMementoToCareTaker() {
		return null;
	}

	@Override
	public void restoreFromMemento(Memento memento) {}
	

}
