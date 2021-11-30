package fr.istic.aco.Commands;

import fr.istic.aco.editor.Engine;
/**
 * Implements the concrete copy selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class CopySelectedTextCommand implements Command {
	
	private Engine engine;
	
	public CopySelectedTextCommand(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void execute() {
		engine.copySelectedText();
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
	

}
