package fr.istic.aco.Commands;

import fr.istic.aco.editor.Engine;

/**
 * Implements the concrete paste from clip board operation on a dependent engine
 * @author Niklas, Johann
 *
 */
public class PasteClipboardCommand implements Command {

	Engine engine;
	
	public PasteClipboardCommand(Engine engine) {
		this.engine = engine;
	}
	@Override
	public void execute() {
		engine.pasteClipboard();
	}

}
