package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Commands.CutSelectedTextCommand;
import fr.istic.aco.Commands.DeleteCommand;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Commands.RedoCommand;
import fr.istic.aco.Commands.UndoCommand;
import fr.istic.aco.Commands.setBeginIndexCommand;
import fr.istic.aco.Commands.setEndIndexCommand;
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
		Command redoCommand = new RedoCommand(undoManager);
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
		Command redoCommand = new RedoCommand(undoManager);
		invoker.addCommandToInvoker("redo", redoCommand);
		
		assertEquals("", engine.getBufferContents());
		
		invoker.play("redo");
		
		assertEquals("", engine.getBufferContents());
	}
	
	@Test
	void redoBeyondHistoryIsEmpy() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		invoker.addCommandToInvoker("redo", redoCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.play("insertCommand");
		invoker.play("insertCommand");
		
		assertEquals("Hello worldHello world", engine.getBufferContents());

		invoker.play("undo");
		invoker.play("undo");
		assertEquals("", engine.getBufferContents());
		
		invoker.play("redo");
		invoker.play("redo");
		invoker.play("redo");
		invoker.play("redo");
		invoker.play("redo");
		assertEquals("Hello worldHello world", engine.getBufferContents());
	}
	
	@Test
	void redoOnceInsertCommand() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(undoManager);
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
	void undoUntilBlankBufferAndRedoAll() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(undoManager);
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
		assertEquals("Hello world", engine.getBufferContents());
		invoker.play("redo");
		assertEquals(" 123Hello world", engine.getBufferContents());
	}
	
	@Test
	void undoRedoDeleteCommand() throws CommandException {
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(undoManager);
		Command deleteCommand = new DeleteCommand(engine, recorder, undoManager);
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("undo", undoCommand);
		invoker.addCommandToInvoker("redo", redoCommand);
		invoker.addCommandToInvoker("deleteCommand", deleteCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("deleteCommand");
		
		assertEquals("lo world", engine.getBufferContents());
		
		invoker.play("undo");
		assertEquals("Hello world", engine.getBufferContents());
		
		invoker.play("redo");
		assertEquals("lo world", engine.getBufferContents());
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
	}
	
	@Test
	void undoRedoCutCommand() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("cut", cutSelectedTextCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		invoker.addCommandToInvoker("undo", undoCommand);
		invoker.addCommandToInvoker("redo", redoCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(1);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("cut");
		
		assertEquals("ello world", engine.getBufferContents());
		
		invoker.play("undo");
		assertEquals("Hello world", engine.getBufferContents());
		invoker.play("redo");
		
		assertEquals("ello world", engine.getBufferContents());
	}
	
	void undoSetEndIndexCommand() throws CommandException {
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command redoCommand = new RedoCommand(undoManager);
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("undo", undoCommand);
		invoker.addCommandToInvoker("redo", redoCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		invoker.play("insertCommand");
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		
		invoker.setEndIndex(10);
		invoker.play("setEndIndex");
		
		invoker.play("undo");
		
		assertEquals("Hello world", engine.getBufferContents());
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
    	
    	invoker.play("redo");
    	assertEquals("Hello world", engine.getBufferContents());
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(10, engine.getSelection().getEndIndex());
	}
	
	
}
