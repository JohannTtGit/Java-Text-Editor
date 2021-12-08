package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.Memento.MementoIndex;
import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.editor.Engine;

public class setEndIndexCommand implements CommandGlobal {
	
	private Engine engine;
	private Invoker invoker;
	private CareTaker caretaker;
	private UndoManager undoManager;
	
	public setEndIndexCommand(Engine engine, Invoker invoker, CareTaker caretaker, UndoManager undoManager) {
		this.engine = engine;
		this.invoker = invoker;
		this.caretaker = caretaker;
		this.undoManager = undoManager;
	}

	@Override
	public void execute() {
		engine.getSelection().setEndIndex(invoker.getEndIndex());
		caretaker.save(this);
		undoManager.save(this);
	}

	@Override
	public Memento sendMementoToCareTaker() {
		return new MementoIndex(this.invoker.getEndIndex());
	}

	@Override
	public void restoreFromMemento(Memento memento) {
		this.invoker.setEndIndex(memento.getIntState());		
	}

}
