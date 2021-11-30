package fr.istic.aco.Memento;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.editor.Engine;

public class Memento {
	
	private Engine engine;
	private Invoker invoker;
	
	public Memento(CommandGlobal command) {
		this.engine = command.getEngine();
		this.invoker = command.getInvoker();
	}
	
}
