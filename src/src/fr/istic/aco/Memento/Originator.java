package fr.istic.aco.Memento;

import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.editor.Engine;

/*
 * This interface is extends by CommandGlobal, and is not implemented.
 * All concrete commands are both Originator and Command.
 * @see : CommandGlobal, Command
 */
public interface Originator {
	
	/* 
	 * @return : New memento containing the command state to be saved.
	 */
	public Memento sendMementoToCareTaker();
	
	/*
	 * 
	 */
	public void restoreFromMemento(Memento memento);
	
	/*
	 * 
	 */
	public Engine getEngine();
	
	/*
	 * 
	 */
	public Invoker getInvoker();
}
