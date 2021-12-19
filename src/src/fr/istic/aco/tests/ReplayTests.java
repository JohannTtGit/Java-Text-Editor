package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.CommandGlobal;
import fr.istic.aco.Commands.CopySelectedTextCommand;
import fr.istic.aco.Commands.CutSelectedTextCommand;
import fr.istic.aco.Commands.DeleteCommand;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Commands.PasteClipboardCommand;
import fr.istic.aco.Commands.ReplayCommand;
import fr.istic.aco.Commands.setBeginIndexCommand;
import fr.istic.aco.Commands.setEndIndexCommand;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Recorder.Recorder;
import fr.istic.aco.Recorder.RecorderImpl;
import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.Undo.UndoManagerImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

class ReplayTests {
	
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
	void replayUniqueInsert() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command replayCommand = new ReplayCommand(recorder);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		recorder.start();
		invoker.play("insertCommand");
		recorder.stop();
		
		invoker.play("replayCommand");
		
		assertEquals("Hello worldHello world", engine.getBufferContents());
	}
	
	@Test
	void replaySuccessiveInserts() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command replayCommand = new ReplayCommand(recorder);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		
		recorder.start();
		
		invoker.setContentToInsert("H");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert("e");
		invoker.play("insertCommand");
		
		invoker.setContentToInsert("l");
		invoker.play("insertCommand");
		
		invoker.play("insertCommand");
		
		invoker.setContentToInsert("o");
		invoker.play("insertCommand");
		
		recorder.stop();
		
		assertEquals("olleH", engine.getBufferContents());
		
		invoker.play("replayCommand");
		
		assertEquals("olleHolleH", engine.getBufferContents());
	}
	
	@Test
	void replayInsertAndCutPaste() throws CommandException{
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, recorder, undoManager);
		CommandGlobal pasteCommand = new PasteClipboardCommand(engine, recorder, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		invoker.addCommandToInvoker("cutSelectedTextCommand", cutSelectedTextCommand);
		invoker.addCommandToInvoker("pasteCommand", pasteCommand);
		
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		invoker.play("insertCommand");
		
		recorder.start();
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("cutSelectedTextCommand");
		invoker.play("pasteCommand");
		
		recorder.stop();
		
		assertEquals("Helworld", engine.getBufferContents());
		
		Command replayCommand = new ReplayCommand(recorder);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		
		invoker.play("replayCommand");
		
		assertEquals("Helld", engine.getBufferContents());
	}
	
	@Test
	void replayInsertAndCopyPaste() throws CommandException{
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine, recorder, undoManager);
		CommandGlobal pasteCommand = new PasteClipboardCommand(engine, recorder, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		invoker.addCommandToInvoker("copySelectedTextCommand", copySelectedTextCommand);
		invoker.addCommandToInvoker("pasteCommand", pasteCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		recorder.start();
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("copySelectedTextCommand");
		invoker.play("pasteCommand");
		
		recorder.stop();
		
		Command replayCommand = new ReplayCommand(recorder);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		
		invoker.play("replayCommand");
		
		assertEquals("Hello worldlo world", engine.getBufferContents());
	}
	
	@Test
	void replayDelete() throws CommandException {
		Command deleteCommand = new DeleteCommand(engine, recorder, undoManager);
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("deleteCommand", deleteCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		invoker.play("insertCommand");
		
		recorder.start();
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("deleteCommand");
		
		recorder.stop();
		
		Command replayCommand = new ReplayCommand(recorder);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		
		invoker.play("replayCommand");
		
		assertEquals("world", engine.getBufferContents());
	}

}
