package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.*;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Memento.CareTakerImpl;
import fr.istic.aco.editor.*;

class CommandPatternTests {
	
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
	void insertCommand() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker, caretaker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		invoker.play("insertCommand");
		
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void setBeginAndEndIndex() throws CommandException {
		Command insertCommand = new InsertCommand(engine, invoker, caretaker);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, caretaker);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, caretaker);
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
        Command copySelectedTextCommand = new CopySelectedTextCommand(engine, caretaker);
        Command insertCommand = new InsertCommand(engine, invoker, caretaker);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, caretaker);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, caretaker);
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
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, caretaker);
        Command insertCommand = new InsertCommand(engine, invoker, caretaker);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, caretaker);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, caretaker);
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
		
		Command deleteCommand = new DeleteCommand(engine, caretaker);
        Command insertCommand = new InsertCommand(engine, invoker, caretaker);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, caretaker);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, caretaker);
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
	void pasteClipboardCommand() throws CommandException {
		Command pasteCommand = new PasteClipboardCommand(engine, caretaker);
		Command insertCommand = new InsertCommand(engine, invoker, caretaker);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, caretaker);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, caretaker);
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, caretaker);
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
	
	

}
