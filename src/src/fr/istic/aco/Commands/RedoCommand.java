package fr.istic.aco.Commands;

import fr.istic.aco.UndoRedo.UndoRedoManager;

public class RedoCommand implements Command {

	private UndoRedoManager undoManager; //Receiver of this command
	
	public RedoCommand(UndoRedoManager undoManager) {
		this.undoManager = undoManager;
	}

	@Override
	public void execute() {
		undoManager.redo();
	}
}
