package fr.istic.aco.Memento;


public class MementoInsert implements Memento {
	
	private String savedContentToInsert;
	
	public MementoInsert(String contentToInsert) {
		this.savedContentToInsert = contentToInsert;
	}
	
	public String getState() {
		return this.savedContentToInsert;
	}
	
}
