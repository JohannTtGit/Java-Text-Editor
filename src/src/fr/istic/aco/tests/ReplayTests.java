package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Commands.ReplayCommand;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Memento.CareTakerImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

class ReplayTests {
	
	Engine engine;
	Invoker invoker;
	CareTakerImpl caretaker;
	
	@BeforeEach
    void setUp() {
        engine = new EngineImpl();
        invoker = new InvokerImpl();
        caretaker = new CareTakerImpl();
    }

	@Test
	void replayOneCommand() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, caretaker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		caretaker.start();
		invoker.play("insertCommand");
		caretaker.stop();
		
		Command replayCommand = new ReplayCommand(caretaker);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		invoker.play("replayCommand");
		
		assertEquals("Hello worldHello world", engine.getBufferContents());
	}

}
