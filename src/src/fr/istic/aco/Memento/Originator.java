package fr.istic.aco.Memento;

/*
 * This interface is extended by CommandGlobal, and is never implemented.
 * All concrete commands are both Originator and Command.
 * @see CommandGlobal
 * @see Command
 */
public interface Originator {
	
	/* 
	 * @return New memento containing the command state to be saved.
	 */
	public Memento sendMementoToCareTaker();
	
	/*
	 * Restore a previous command state
	 */
	public void restoreFromMemento(Memento memento);
}
