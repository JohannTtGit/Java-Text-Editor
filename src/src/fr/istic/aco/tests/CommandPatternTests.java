package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.*;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Recorder.Recorder;
import fr.istic.aco.Recorder.RecorderImpl;
import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.Undo.UndoManagerImpl;
import fr.istic.aco.editor.*;

class CommandPatternTests {
	
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
	void insertCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		invoker.play("insertCommand");
		
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void setBeginAndEndIndex() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
	}
	
	@Test
	void CopySelectedTextCommand() throws CommandException {
        Command copySelectedTextCommand = new CopySelectedTextCommand(engine, recorder, undoManager);
        Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("copySelectedTextCommand", copySelectedTextCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
        
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(1);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("copySelectedTextCommand");
		
		assertEquals("H", engine.getClipboardContents());
	}
	
	@Test
	void CutSelectedTextCommand() throws CommandException {
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, recorder, undoManager);
        Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("cutSelectedTextCommand", cutSelectedTextCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
        
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(1);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("cutSelectedTextCommand");
				
		assertEquals("ello world", engine.getBufferContents());
	}
	
	@Test
	void deleteCommand() throws CommandException {
		
		Command deleteCommand = new DeleteCommand(engine, recorder, undoManager);
        Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
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
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("deleteCommand");
		
		assertEquals("lo world", engine.getBufferContents());
	}
	
	@Test
	void cutAndPast() throws CommandException {
		Command pasteCommand = new PasteClipboardCommand(engine, recorder, undoManager);
		Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, recorder, undoManager);
		invoker.addCommandToInvoker("pasteCommand", pasteCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		invoker.addCommandToInvoker("cutSelectedTextCommand", cutSelectedTextCommand);
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
        invoker.play("cutSelectedTextCommand");
        invoker.play("pasteCommand");
        
        assertEquals("Helworld", engine.getBufferContents());
	}
	
	@Test
	void copyAndPast() throws CommandException {
        Command copySelectedTextCommand = new CopySelectedTextCommand(engine, recorder, undoManager);
        Command pasteClipboardCommand = new PasteClipboardCommand(engine, recorder, undoManager);
        Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
		invoker.addCommandToInvoker("copySelectedTextCommand", copySelectedTextCommand);
		invoker.addCommandToInvoker("pasteClipboardCommand", pasteClipboardCommand);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
        
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(1);
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("copySelectedTextCommand");
		invoker.play("pasteClipboardCommand");
		
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	

}
