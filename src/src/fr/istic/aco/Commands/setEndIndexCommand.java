package fr.istic.aco.Commands;

import fr.istic.aco.Memento.Memento;
import fr.istic.aco.editor.Engine;

public class setEndIndexCommand implements CommandGlobal {
	
	private Engine engine;
	private Invoker invoker;
	
	public setEndIndexCommand(Engine engine, Invoker invoker) {
		this.engine = engine;
		this.invoker = invoker;
	}

	@Override
	public void execute() {
		engine.getSelection().setEndIndex(invoker.getEndIndex());
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
