package fr.istic.aco.Commands;

import fr.istic.aco.editor.Engine;

/**
 * Implements the delete operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class DeleteCommand implements Command {

	private Engine engine;
	
	public DeleteCommand(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void execute() {
		engine.delete();
	}
	
}
