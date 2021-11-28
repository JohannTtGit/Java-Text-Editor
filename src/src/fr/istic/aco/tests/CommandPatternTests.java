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
        invoker = new InvokerImpl();
    }
	
	@Test
	void insertCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		invoker.play("insertCommand");
		
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void setBeginAndEndIndex() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		invoker.play("insertCommand");
		
		Command setBeginIndex = new setBeginIndexCommand(engine, 0);
		Command setEndIndex = new setEndIndexCommand(engine, 3);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
	}
	
	@Test
	void CopySelectedTextCommand() throws CommandException {
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine);
        Command insertCommand = new InsertCommand(engine, invoker);
        invoker.addCommandToInvoker("insertCommand", insertCommand);
        invoker.addCommandToInvoker("insertCommand", insertCommand);
        invoker.addCommandToInvoker("copySelectedTextCommand", copySelectedTextCommand);
        invoker.setContentToInsert("Hello world");
        
		invoker.play("insertCommand");
		
		Command setBeginIndex = new setBeginIndexCommand(engine, 0);
		Command setEndIndex = new setEndIndexCommand(engine, 1);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
        
		invoker.play("copySelectedTextCommand");
		assertEquals("H", engine.getClipboardContents());
	}
	
	@Test
	void CutSelectedTextCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		invoker.play("insertCommand");
		
		Command setBeginIndex = new setBeginIndexCommand(engine, 0);
		Command setEndIndex = new setEndIndexCommand(engine, 1);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
        
        Command cutSelectedTextCommand = new CutSelectedTextCommand(engine);
        invoker.addCommandToInvoker("cutSelectedTextCommand", cutSelectedTextCommand);
        
		invoker.play("cutSelectedTextCommand");
		
		assertEquals("ello world", engine.getBufferContents());
	}
	
	@Test
	void deleteCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		invoker.play("insertCommand");
		
		Command setBeginIndex = new setBeginIndexCommand(engine, 0);
		Command setEndIndex = new setEndIndexCommand(engine, 3);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
        
        Command deleteCommand = new DeleteCommand(engine);
        invoker.addCommandToInvoker("deleteCommand", deleteCommand);
        invoker.addCommandToInvoker("insertCommand", insertCommand);
        
		invoker.play("deleteCommand");
		
		assertEquals("lo world", engine.getBufferContents());
	}
	
	@Test
	void pasteClipboardCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		invoker.play("insertCommand");
		
		Command setBeginIndex = new setBeginIndexCommand(engine, 0);
		Command setEndIndex = new setEndIndexCommand(engine, 3);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
        
        Command pasteCommand = new PasteClipboardCommand(engine);
        invoker.addCommandToInvoker("pasteCommand", pasteCommand);
        
		invoker.play("pasteCommand");
		assertEquals("", engine.getClipboardContents());
		assertEquals("lo world", engine.getBufferContents());
		
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine);
		invoker.addCommandToInvoker("copySelectedTextCommand", copySelectedTextCommand);
		
		invoker.play("copySelectedTextCommand");
		
		assertEquals("lo ", engine.getClipboardContents());
		assertEquals(0, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());
    	
		invoker.play("pasteCommand");
		
		assertEquals("lo world", engine.getBufferContents());
	}
	
	

}
