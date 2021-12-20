package fr.istic.aco.utileFunctions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;

/**
 * Class providing useful list methods
 * @author Johann
 *
 */
public class UtileFunctions {
	
	/**
	 * Process a CommandGlobal ArrayList deep copy
	 * @param arrayListToCopy
	 * @return Deep copied CommandGlobal ArrayList
	 */
	public static ArrayList<CommandGlobal> deepCommandsArrayListCopy(List<CommandGlobal> arrayListToCopy) {
		
		ArrayList<CommandGlobal> result = new ArrayList<>();
		
		Iterator<CommandGlobal> iterator = arrayListToCopy.iterator();
		
		while(iterator.hasNext()) {
			result.add(iterator.next());
		}
		
		return result;
	}
	
	/**
	 * Process a Memento ArrayList deep copy
	 * @param arrayListToCopy
	 * @return Deep copied Memento ArrayList
	 */
	public static ArrayList<Memento> deepMementosArrayListCopy(List<Memento> arrayListToCopy) {
		
		ArrayList<Memento> result = new ArrayList<>();
		
		Iterator<Memento> iterator = arrayListToCopy.iterator();
		
		while(iterator.hasNext()) {
			result.add(iterator.next());
		}
		
		return result;
	}
}