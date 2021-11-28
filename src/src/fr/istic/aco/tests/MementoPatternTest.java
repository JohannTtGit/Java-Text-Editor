package fr.istic.aco.tests;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Selection.SelectionState;
import fr.istic.aco.Selection.SelectionStateImpl;
import fr.istic.aco.Selection.SelectionStates;
import fr.istic.aco.Selection.SelectionStatesImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

public class MementoPatternTest {
	
	Engine engine;
	Invoker invoker;
	SelectionStates caretaker;
	SelectionState originator;

	@BeforeEach
    void setUp() {
        engine = new EngineImpl();
        invoker = new InvokerImpl();
        caretaker = new SelectionStatesImpl();
        originator = new SelectionStateImpl();
    }
	
}
