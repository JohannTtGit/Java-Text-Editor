package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete paste from clip board operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class PasteClipboardCommand implements CommandGlobal {

	private Engine engine;
	private CareTaker caretaker;
	private UndoManager undoManager;
	
	public PasteClipboardCommand(Engine engine, CareTaker caretaker, UndoManager undoManager) {
		this.engine = engine;
		this.caretaker = caretaker;
		this.undoManager = undoManager;
	}
	@Override
	public void execute() {
		engine.pasteClipboard();
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
