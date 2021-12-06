package fr.istic.aco.Commands;

import fr.istic.aco.Memento.Originator;

/*
 * @author : Johann, Niklas
 * Enable a concrete command to be both a Command and an Originator, in the context of both Command and Memento design patterns.
 * As part of the Command design pattern, CommandGlobal allows to be a command.
 * As part of the Memento design pattern, CommandGlobal allows to be an Originator. 
 */
public interface CommandGlobal extends Command, Originator {}
