package fr.istic.aco.Commands;

import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.editor.Engine;

public class UndoCommand implements Command {
	
	private Engine engine; //Not the receiver of this command, but necessary to clear the buffer
	private UndoManager undoManager; //Receiver of this command
	
	public UndoCommand(Engine engine, UndoManager undoManager) {
		this.engine = engine;
		this.undoManager = undoManager;
	}

	@Override
	public void execute() {
		engine.clearBuffer();
		undoManager.undo();
	}

}
