package fr.istic.aco.Commands;

import fr.istic.aco.editor.Engine;

public class setBeginIndexCommand implements Command {
	
	Engine engine;
	int beginIndex;
	
	public setBeginIndexCommand(Engine engine, int beginIndex) {
		this.engine = engine;
		this.beginIndex = beginIndex;
	}

	@Override
	public void execute() {
		engine.getSelection().setBeginIndex(beginIndex);
	}
}
