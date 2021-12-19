package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.UndoRedo.UndoRedoManager;
import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete cut selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class CutSelectedTextCommand implements CommandGlobal {

	private Engine engine;
	private CareTaker caretaker;
	private UndoRedoManager undoManager;
	
	public CutSelectedTextCommand(Engine engine, CareTaker caretaker, UndoRedoManager undoManager) {
		this.engine = engine;
		this.caretaker = caretaker;
		this.undoManager = undoManager;
	}
	
	@Override
	public void execute() {
		engine.cutSelectedText();
		caretaker.save(this);
		undoManager.save(this);
	}


	@Override
	public Memento sendMementoToCareTaker() {
		return null;
	}


	@Override
	public void restoreFromMemento(Memento memento) {}

}
