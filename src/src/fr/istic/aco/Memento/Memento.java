package fr.istic.aco.Memento;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.editor.Engine;

public class Memento {
	
	private Engine engine;
	private Invoker invoker;
	
	public Memento(Command command) {
		this.engine = command.getEngine();
		this.invoker = command.getInvoker();
	}
	
}
