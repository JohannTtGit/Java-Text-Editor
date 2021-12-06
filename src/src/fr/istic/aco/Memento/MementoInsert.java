package fr.istic.aco.Memento;


public class MementoInsert {
	
	private String savedContentToInsert;
	
	public MementoInsert(String contentToInsert) {
		this.savedContentToInsert = contentToInsert;
	}
	
	public String getState() {
		return this.savedContentToInsert;
	}
	
}
