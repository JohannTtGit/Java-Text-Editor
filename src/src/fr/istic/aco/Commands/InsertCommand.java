package fr.istic.aco.Commands;

import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete copy selected text operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class InsertCommand implements Command {

	private Engine engine;
	private final String toInsert;
	
	public InsertCommand(Engine textEngine, String toInsert) {
		this.engine = textEngine;
		this.toInsert = toInsert;
	}
	
	@Override
	public void execute() {
		this.engine.insert(toInsert);
	}

}
