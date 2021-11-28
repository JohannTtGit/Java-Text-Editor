package fr.istic.aco.Commands;

import fr.istic.aco.Selection.Selection;

public class setBeginIndexCommand implements Command {
	
	Selection selection;
	int beginIndex;
	
	public setBeginIndexCommand(Selection selection, int beginIndex) {
		this.selection = selection;
		this.beginIndex = beginIndex;
	}

	@Override
	public void execute() {
		selection.setBeginIndex(beginIndex);
	}
}
