package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete copy selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class CopySelectedTextCommand implements CommandGlobal {
	
	private Engine engine;
	private CareTaker caretaker;
	
	public CopySelectedTextCommand(Engine engine, CareTaker caretaker) {
		this.engine = engine;
		this.caretaker = caretaker;
	}

	@Override
	public void execute() {
		caretaker.save(this);
		engine.copySelectedText();
	}


	@Override
	public Memento sendMementoToCareTaker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreFromMemento(Memento memento) {
		// TODO Auto-generated method stub
		
	}
	

}
