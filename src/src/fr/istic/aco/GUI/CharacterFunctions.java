package fr.istic.aco.GUI;

public class CharacterFunctions {
	
	public static boolean isCorrecteSelection(String strSelection) {
		
		if(strSelection.length() >= 3 || strSelection.length() <= 5) {
			
			//Wanted selection shape is of the form 1-9 (One digit two times)
			if(strSelection.length() == 3) {
				if(Character.isDigit(strSelection.charAt(0)) && Character.isDigit(strSelection.charAt(2))) {
					return true;
				}
				return false;
			}
			
			//If selection is of the form 12-23 (two digits two times)
			else if(strSelection.length() == 5) {
				if(Character.isDigit(strSelection.charAt(0)) && Character.isDigit(strSelection.charAt(1)) && Character.isDigit(strSelection.charAt(3)) && Character.isDigit(strSelection.charAt(4))) {
					return true;
				}
				return false;
			}
			
		}
		
		return false;
	}
	
	//This function is called only if isCorrectSelection sent true
	public static int[] getSelectionIndex(String strSelection) {
		
		int result[] = new int[2];
		
		if(strSelection.length() == 3) {
			result[0] = (int) strSelection.charAt(0) - '0';
			result[1] = (int) strSelection.charAt(2) - '0';
			return result;
		}
		
		//If selection is of the form 12-23 (two digits two times)
		else if(strSelection.length() == 5) {
			
			int beginIndex = Integer.parseInt(strSelection.substring(0, 2));
			int endIndex = Integer.parseInt(strSelection.substring(4, 6));
			result[0] = beginIndex;
			result[1] = endIndex;
			return result;
		}
		
		return result;
	}
}
