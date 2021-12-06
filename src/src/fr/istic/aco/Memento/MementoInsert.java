package fr.istic.aco.Memento;


/*
 * Memento interface implementation, dedicated to the concrete command "InsertCommand".
 * @see : Memento, InsertCommand
 */
public class MementoInsert implements Memento {
	
	private String savedContentToInsert;
	
	public MementoInsert(String contentToInsert) {
		this.savedContentToInsert = contentToInsert;
	}
	
	@Override
	public String getState() {
		return this.savedContentToInsert;
	}
	
}
