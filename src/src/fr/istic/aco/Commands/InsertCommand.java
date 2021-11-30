package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTaker;
import fr.istic.aco.Memento.Memento;
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
		caretaker.addMemento(new Memento(this));
	}

	@Override
	public Engine getEngine() {
		return this.engine;
	}

	@Override
	public Invoker getInvoker() {
		return this.invoker;
	}

}
