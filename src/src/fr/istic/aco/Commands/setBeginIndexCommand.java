package fr.istic.aco.Commands;

import fr.istic.aco.Memento.Memento;
import fr.istic.aco.editor.Engine;

public class setBeginIndexCommand implements CommandGlobal {
	
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

	@Override
	public Memento sendMementoToCareTaker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreFromMemento(Memento memento) {
		// TODO Auto-generated method stub
		
	}

}
