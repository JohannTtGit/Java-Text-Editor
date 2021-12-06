package fr.istic.aco.Commands;

import fr.istic.aco.Memento.CareTakerImpl;
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
	private CareTakerImpl caretaker; //Needed as part of the Memento design pattern
	
	public InsertCommand(Engine textEngine, Invoker invoker, CareTakerImpl caretaker) {
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
	public void restoreFromMemento(MementoInsert memento) {
		this.invoker.setContentToInsert(memento.getState());
	}
	
	@Override
	public MementoInsert sendMementoToCareTaker() {
		return new MementoInsert(this.invoker.getContentToInsert());
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
