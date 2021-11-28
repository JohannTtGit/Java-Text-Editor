package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.*;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Exceptions.SelectionStateException;
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
	void insertCommand() throws CommandException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.setContentToInsert("Hello world");
		invoker.play(insertCommand);
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void setBeginAndEndIndex() throws CommandException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.setContentToInsert("Hello world");
		invoker.play(insertCommand);
		
		Command setBeginIndex = new setBeginIndexCommand(engine.getSelection(), 0);
		Command setEndIndex = new setEndIndexCommand(engine.getSelection(), 3);
		
		invoker.play(setEndIndex);
		invoker.play(setBeginIndex);
		
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
	}
	
	@Test
	void CopySelectedTextCommand() throws CommandException, SelectionStateException {
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine);
        Command insertCommand = new InsertCommand(engine, invoker);
        invoker.setContentToInsert("Hello world");
		invoker.play(insertCommand);
		
		Command setBeginIndex = new setBeginIndexCommand(engine.getSelection(), 0);
		Command setEndIndex = new setEndIndexCommand(engine.getSelection(), 1);
		
		invoker.play(setEndIndex);
		invoker.play(setBeginIndex);
        
		invoker.play(copySelectedTextCommand);
		assertEquals("H", engine.getClipboardContents());
	}
	
	@Test
	void CutSelectedTextCommand() throws CommandException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.setContentToInsert("Hello world");
		invoker.play(insertCommand);
		
		Command setBeginIndex = new setBeginIndexCommand(engine.getSelection(), 0);
		Command setEndIndex = new setEndIndexCommand(engine.getSelection(), 1);
		
		invoker.play(setEndIndex);
		invoker.play(setBeginIndex);
        
        Command cutSelectedTextCommand = new CutSelectedTextCommand(engine);
		invoker.play(cutSelectedTextCommand);
		assertEquals("ello world", engine.getBufferContents());
	}
	
	@Test
	void deleteCommand() throws CommandException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.setContentToInsert("Hello world");
		invoker.play(insertCommand);
		
		Command setBeginIndex = new setBeginIndexCommand(engine.getSelection(), 0);
		Command setEndIndex = new setEndIndexCommand(engine.getSelection(), 3);
		
		invoker.play(setEndIndex);
		invoker.play(setBeginIndex);
        
        Command deleteCommand = new DeleteCommand(engine);
		invoker.play(deleteCommand);
		assertEquals("lo world", engine.getBufferContents());
	}
	
	@Test
	void pasteClipboardCommand() throws CommandException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.setContentToInsert("Hello world");
		invoker.play(insertCommand);
		
		Command setBeginIndex = new setBeginIndexCommand(engine.getSelection(), 0);
		Command setEndIndex = new setEndIndexCommand(engine.getSelection(), 3);
		
		invoker.play(setEndIndex);
		invoker.play(setBeginIndex);
        
        Command pasteCommand = new PasteClipboardCommand(engine);
		invoker.play(pasteCommand);
		assertEquals("", engine.getClipboardContents());
		assertEquals("lo world", engine.getBufferContents());
		
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine);
		invoker.play(copySelectedTextCommand);
		assertEquals("lo ", engine.getClipboardContents());
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
    	
		invoker.play(new PasteClipboardCommand(engine));
		assertEquals("lo world", engine.getBufferContents());
	}
	
	

}
