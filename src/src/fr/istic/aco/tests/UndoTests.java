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
import fr.istic.aco.Recorder.Recorder;
import fr.istic.aco.Recorder.RecorderImpl;
import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.Undo.UndoManagerImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

class UndoTests {
	
	Engine engine;
	Invoker invoker;
	Recorder recorder;
	UndoManager undoManager;
	
	@BeforeEach
    void setUp() {
        engine = new EngineImpl();
        invoker = new InvokerImpl();
        recorder = new RecorderImpl();
        undoManager = new UndoManagerImpl();
    }
	
	@Test
	void undoSimple() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		CommandGlobal undoCommand = new UndoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.setContentToInsert("Hello world.");
		
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" How are you ?");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" Let us code");
		invoker.play("insertCommand");
		
		invoker.play("undo");
		
		assertEquals("Hello world. How are you ?", engine.getBufferContents());
	}

}
