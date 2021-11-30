package fr.istic.aco.Commands;

import fr.istic.aco.editor.Engine;

public class setEndIndexCommand implements Command {
	
	Engine engine;
	int endIndex;
	
	public setEndIndexCommand(Engine engine, int endIndex) {
		this.engine = engine;
		this.endIndex = endIndex;
	}

	@Override
	public void execute() {
		engine.getSelection().setEndIndex(endIndex);
	}

	@Override
	public Engine getEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoker getInvoker() {
		// TODO Auto-generated method stub
		return null;
	}

}
