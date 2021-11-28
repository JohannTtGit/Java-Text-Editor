package fr.istic.aco.Commands;

import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete copy selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class InsertCommand implements Command {

	private Engine engine;
	private Invoker invoker;
	
	public InsertCommand(Engine textEngine, Invoker invoker) {
		this.engine = textEngine;
		this.invoker = invoker;
	}
	
	@Override
	public void execute() {
		this.engine.insert(invoker.getContentToInsert());
	}

}
