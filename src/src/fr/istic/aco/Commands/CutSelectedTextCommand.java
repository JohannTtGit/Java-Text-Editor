package fr.istic.aco.Commands;

import fr.istic.aco.Memento.Memento;
import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete cut selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class CutSelectedTextCommand implements CommandGlobal {

	private Engine engine;
	
	public CutSelectedTextCommand(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void execute() {
		engine.cutSelectedText();
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
	public Memento sendMementoToCareTaker() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void restoreFromMemento(Memento memento) {
		// TODO Auto-generated method stub
		
	}

}
