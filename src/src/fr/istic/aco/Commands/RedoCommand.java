package fr.istic.aco.Commands;

import fr.istic.aco.UndoRedo.UndoRedoManager;
import fr.istic.aco.editor.Engine;

public class RedoCommand implements Command {

	private Engine engine; //Not the receiver of this command, but necessary to clear the buffer
	private UndoRedoManager undoManager; //Receiver of this command
	
	public RedoCommand(Engine engine, UndoRedoManager undoManager) {
		this.engine = engine;
		this.undoManager = undoManager;
	}

	@Override
	public void execute() {
		undoManager.redo();
	}
}
