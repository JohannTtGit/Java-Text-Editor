package fr.istic.aco.Memento;

/*
 * Interface implemented by all respective concrete command Mementos
 */
public interface Memento {

	/*
	 * @return Textual state of a Memento
	 */
	public String getState();
	
	/*
	 * @return Numerical state of a Memento
	 */
	public int getIntState();

}
