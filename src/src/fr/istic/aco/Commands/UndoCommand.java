package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.editor.Engine;

public class UndoCommand implements CommandGlobal {
	
	private Engine engine; //Not the receiver of this command, but necessary to clear the buffer
	private CareTaker caretaker; //Receiver of this command
	
	public UndoCommand(Engine engine, CareTaker caretaker) {
		this.engine = engine;
		this.caretaker = caretaker;
	}

	@Override
	public void execute() {
		engine.clearBuffer();
		caretaker.undo();
	}

	@Override
	public Memento sendMementoToCareTaker() {
		return null;
	}

	@Override
	public void restoreFromMemento(Memento memento) {}

}
