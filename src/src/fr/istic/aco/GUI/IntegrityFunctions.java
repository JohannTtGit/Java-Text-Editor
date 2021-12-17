package fr.istic.aco.GUI;

public class IntegrityFunctions {
	
	public static boolean isCorrecteSelection(String strSelection) {
		
		if(strSelection.length() >= 3 || strSelection.length() <= 5) {
			return true;
		}
		
		return false;
	}
}
