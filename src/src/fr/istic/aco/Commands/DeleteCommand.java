package fr.istic.aco.Commands;

import fr.istic.aco.Memento.Memento;
import fr.istic.aco.editor.Engine;

/**
 * Implements the delete operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class DeleteCommand implements CommandGlobal {

	private Engine engine;
	
	public DeleteCommand(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void execute() {
		engine.delete();
	}

	@Override
	public Engine getEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoker getInvoker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreFromMemento(Memento memento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Memento sendMementoToCareTaker() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
