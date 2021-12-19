package fr.istic.aco.Commands;

import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.editor.Engine;

public class RedoCommand implements Command {

	private Engine engine; //Not the receiver of this command, but necessary to clear the buffer
	private UndoManager undoManager; //Receiver of this command
	
	public RedoCommand(Engine engine, UndoManager undoManager) {
		this.engine = engine;
		this.undoManager = undoManager;
	}

	@Override
	public void execute() {
		engine.clearBuffer();
		engine.getSelection().setEndIndex(0);
		engine.getSelection().setBeginIndex(0);
		undoManager.redo();
	}
}
