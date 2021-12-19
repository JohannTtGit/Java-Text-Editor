package fr.istic.aco.utileFunctions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Memento.Memento;

public class UtileFunctions {
	
	public static ArrayList<CommandGlobal> deepCommandsArrayListCopy(List<CommandGlobal> arrayListToCopy) {
		
		ArrayList<CommandGlobal> result = new ArrayList<>();
		
		Iterator<CommandGlobal> iterator = arrayListToCopy.iterator();
		
		while(iterator.hasNext()) {
			result.add(iterator.next());
		}
		
		return result;
	}
	
	public static ArrayList<Memento> deepMementosArrayListCopy(List<Memento> arrayListToCopy) {
		
		ArrayList<Memento> result = new ArrayList<>();
		
		Iterator<Memento> iterator = arrayListToCopy.iterator();
		
		while(iterator.hasNext()) {
			result.add(iterator.next());
		}
		
		return result;
	}
}