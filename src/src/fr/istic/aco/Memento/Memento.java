package fr.istic.aco.Memento;

/*
 * Interface implemented by all respective concrete command Mementos
 */
public interface Memento {

	/*
	 * @return : Textual state of a Memento
	 */
	String getState();
	
	/*
	 * @return : Numerical state of a Memento
	 */
	int getIntState();

}
