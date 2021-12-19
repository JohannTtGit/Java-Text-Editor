package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Commands.RedoCommand;
import fr.istic.aco.Commands.UndoCommand;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Recorder.Recorder;
import fr.istic.aco.Recorder.RecorderImpl;
import fr.istic.aco.UndoRedo.UndoRedoManager;
import fr.istic.aco.UndoRedo.UndoRedoManagerImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

public class RedoTests {

	Engine engine;
	Invoker invoker;
	Recorder recorder;
	UndoRedoManager undoManager;
	
	@BeforeEach
    void setUp() {
        engine = new EngineImpl();
        invoker = new InvokerImpl();
        recorder = new RecorderImpl();
        undoManager = new UndoRedoManagerImpl();
    }
	
	@Test
	void redoWithoutHavingUndone() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command redoCommand = new RedoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("redo", redoCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.play("insertCommand");
		
		assertEquals("Hello world", engine.getBufferContents());
		
		invoker.play("redo");
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void redoEmptyBuffer() throws CommandException {
		Command redoCommand = new RedoCommand(engine, undoManager);
		invoker.addCommandToInvoker("redo", redoCommand);
		
		assertEquals("", engine.getBufferContents());
		
		invoker.play("redo");
		
		assertEquals("", engine.getBufferContents());
	}
	
	@Test
	void redoOnceInsertCommand() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		invoker.addCommandToInvoker("redo", redoCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.play("insertCommand");
		
		assertEquals("Hello world", engine.getBufferContents());
		
		invoker.play("undo");
		assertEquals("", engine.getBufferContents());
		
		invoker.play("redo");
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void undoRedoTwoTimesInsertCommand() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		invoker.addCommandToInvoker("redo", redoCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" 123");
		invoker.play("insertCommand");
		
		assertEquals(" 123Hello world", engine.getBufferContents());

		invoker.play("undo");
		invoker.play("undo");
		assertEquals("", engine.getBufferContents());
		
		invoker.play("redo");
		invoker.play("redo");
		assertEquals("Hello world 123", engine.getBufferContents());
	}
	
	
}
