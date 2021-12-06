package fr.istic.aco.Commands;

import fr.istic.aco.Memento.Originator;

/*
 * @author : Johann, Niklas
 * Enable a concret command to be both a Command and an Originator, in the context of both Command and Memento design patterns
 */
public interface CommandGlobal extends Command, Originator {
	
	
}
