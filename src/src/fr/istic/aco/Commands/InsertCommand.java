package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
import fr.istic.aco.Memento.MementoInsert;
import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete copy selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class InsertCommand implements CommandGlobal{

	private Engine engine;
	private Invoker invoker;
	private CareTaker caretaker; //Needed as part of the Memento design pattern
	
	public InsertCommand(Engine textEngine, Invoker invoker, CareTaker caretaker) {
		this.engine = textEngine;
		this.invoker = invoker;
		this.caretaker = caretaker;
	}
	
	@Override
	public void execute() {
		this.engine.insert(invoker.getContentToInsert());
		caretaker.save(this);
	}
	
	@Override
	public void restoreFromMemento(Memento memento) {
		this.invoker.setContentToInsert(memento.getState());
	}
	
	@Override
	public Memento sendMementoToCareTaker() {
		return new MementoInsert(this.invoker.getContentToInsert());
	}

}
