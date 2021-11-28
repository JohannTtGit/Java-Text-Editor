package fr.istic.aco.Commands;

import fr.istic.aco.Selection.Selection;

public class setEndIndexCommand implements Command {
	
	Selection selection;
	int endIndex;
	
	public setEndIndexCommand(Selection selection, int endIndex) {
		this.selection = selection;
		this.endIndex = endIndex;
	}

	@Override
	public void execute() {
		selection.setEndIndex(endIndex);
	}
}
