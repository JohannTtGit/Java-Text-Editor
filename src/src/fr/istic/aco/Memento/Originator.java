package fr.istic.aco.Memento;

import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.editor.Engine;

public interface Originator {
	public MementoInsert sendMementoToCareTaker();
	public void restoreFromMemento(MementoInsert memento);
	public Engine getEngine();
	public Invoker getInvoker();
}
