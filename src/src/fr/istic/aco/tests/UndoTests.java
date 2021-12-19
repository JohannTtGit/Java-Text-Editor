package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Commands.CutSelectedTextCommand;
import fr.istic.aco.Commands.DeleteCommand;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
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

class UndoTests {
	
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
	void undoInsertCommand() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.setContentToInsert("Hello world.");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" How are you ?");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" Let us code");
		invoker.play("insertCommand");
		
		invoker.play("undo");
		
		assertEquals(" How are you ?Hello world.", engine.getBufferContents());
	}
	
	@Test
	void undoInsertCommandTwice() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.setContentToInsert("Hello world.");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" How are you ?");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" Let us code");
		invoker.play("insertCommand");
		
		invoker.play("undo");
		invoker.play("undo");
		
		assertEquals("Hello world.", engine.getBufferContents());
	}
	
	
	@Test
	void undoUntilBlankDocument() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.setContentToInsert("Hello world.");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" How are you ?");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert(" Let us code");
		invoker.play("insertCommand");
		
		invoker.play("undo");
		invoker.play("undo");
		invoker.play("undo");
		
		assertEquals("", engine.getBufferContents());
	}
	
	@Test
	void undoSeveralTimesAnEmptyDocument() throws CommandException {
		Command undoCommand = new UndoCommand(engine, undoManager);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.play("undo");
		invoker.play("undo");
		invoker.play("undo");
		
		assertEquals("", engine.getBufferContents());
	}
	
	@Test
	void undoSuccessiveInserts() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.setContentToInsert("H");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert("e");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert("l");
		invoker.play("insertCommand");
		
		invoker.play("insertCommand");
		
		invoker.setContentToInsert("o");
		invoker.play("insertCommand");
		
		assertEquals("olleH", engine.getBufferContents());
		
		invoker.play("undo");
		
		assertEquals("lleH", engine.getBufferContents());
	}
	
	@Test
	void undoDeleteCommand() throws CommandException {
		Command undoCommand = new UndoCommand(engine, undoManager);
		Command deleteCommand = new DeleteCommand(engine, recorder, undoManager);
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("undo", undoCommand);
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
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
	}
	
	@Test
	void undoSetEndIndexCommand() throws CommandException {
		Command undoCommand = new UndoCommand(engine, undoManager);
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("undo", undoCommand);
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
	}
	
	@Test
	void undoCutCommand() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		Command undoCommand = new UndoCommand(engine, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("cut", cutSelectedTextCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		invoker.addCommandToInvoker("undo", undoCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(1);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("cut");
		
		assertEquals("ello world", engine.getBufferContents());
	}


}
