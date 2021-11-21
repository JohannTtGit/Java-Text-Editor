package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.*;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.editor.*;

class CommandPatternTests {
	
	Engine engine;
	Invoker invoker;

	@BeforeEach
    void setUp() {
        engine = new EngineImpl();
        invoker = new InvokerImpl(engine);
    }
	
	@Test
	void insertCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
		invoker.execute(insertCommand);
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void CopySelectedTextCommand() throws CommandException {
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine);
        Command insertCommand = new InsertCommand(engine, "Hello world");
		invoker.execute(insertCommand);
		
		engine.getSelection().setEndIndex(1);
        engine.getSelection().setBeginIndex(0);
        
		invoker.execute(copySelectedTextCommand);
		assertEquals("H", engine.getClipboardContents());
	}
	
	@Test
	void CutSelectedTextCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
		invoker.execute(insertCommand);
		
		engine.getSelection().setEndIndex(1);
        engine.getSelection().setBeginIndex(0);
        
        Command cutSelectedTextCommand = new CutSelectedTextCommand(engine);
		invoker.execute(cutSelectedTextCommand);
		assertEquals("ello world", engine.getBufferContents());
	}
	
	@Test
	void deleteCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
		invoker.execute(insertCommand);
		
		engine.getSelection().setEndIndex(3);
        engine.getSelection().setBeginIndex(0);
        
        Command deleteCommand = new DeleteCommand(engine);
		invoker.execute(deleteCommand);
		assertEquals("lo world", engine.getBufferContents());
	}
	
	@Test
	void pasteClipboardCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
		invoker.execute(insertCommand);
		
		engine.getSelection().setEndIndex(3);
        engine.getSelection().setBeginIndex(0);
        
        Command pasteCommand = new PasteClipboardCommand(engine);
		invoker.execute(pasteCommand);
		assertEquals("", engine.getClipboardContents());
		assertEquals("lo world", engine.getBufferContents());
		
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine);
		invoker.execute(copySelectedTextCommand);
		assertEquals("lo ", engine.getClipboardContents());
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
    	
		invoker.execute(new PasteClipboardCommand(engine));
		assertEquals("lo world", engine.getBufferContents());
	}
	
	

}
