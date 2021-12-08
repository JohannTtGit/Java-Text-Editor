package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Commands.UndoCommand;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Memento.CareTakerImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

class UndoTests {
	
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
	void undoSimple() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, caretaker);
		CommandGlobal undoCommand = new UndoCommand(engine, caretaker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.setContentToInsert("Hello world.");
		
		caretaker.start();
		
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" How are you ?");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" Let us code");
		invoker.play("insertCommand");
		
		caretaker.stop();
		
		invoker.play("undo");
		
		assertEquals("Hello world. How are you ?", engine.getBufferContents());
	}

}
