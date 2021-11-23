package fr.istic.aco.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.CutSelectedTextCommand;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Exceptions.CommandHistoryException;
import fr.istic.aco.Exceptions.SelectionStateException;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

class ReplayAndUndoTest {

	Engine engine;
	Invoker invoker;

	@BeforeEach
    void setUp() {
        engine = new EngineImpl();
        invoker = new InvokerImpl(engine);
    }
	
	@Test
	void replaySimpleInsertion() throws CommandException, CommandHistoryException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
		invoker.execute(insertCommand);
		assertEquals("Hello world", engine.getBufferContents());
		invoker.replay();
		assertEquals("Hello world", engine.getBufferContents());
	}
	
	@Test
	void replay() throws CommandException, CommandHistoryException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
	    Command cutSelectedTextCommand = new CutSelectedTextCommand(engine);
        
        invoker.execute(insertCommand);
        engine.getSelection().setEndIndex(1);
        engine.getSelection().setBeginIndex(0);
        invoker.execute(cutSelectedTextCommand);
        
        assertEquals("ello world", engine.getBufferContents());
        invoker.replay();
        assertEquals("ello world", engine.getBufferContents());
  
	}
	
	@Test
	void replay2() throws CommandException, CommandHistoryException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
	    Command cutSelectedTextCommand = new CutSelectedTextCommand(engine);
	    Command cutSelectedTextCommand2 = new CutSelectedTextCommand(engine);
        
        invoker.execute(insertCommand);
        
        engine.getSelection().setEndIndex(1);
        engine.getSelection().setBeginIndex(0);
        invoker.execute(cutSelectedTextCommand);
        
        engine.getSelection().setEndIndex(3);
        engine.getSelection().setBeginIndex(2);
        invoker.execute(cutSelectedTextCommand2);
        
        assertEquals("elo world", engine.getBufferContents());
        invoker.replay();
        assertEquals("elo world", engine.getBufferContents());
  
	}
	
	@Test
	void undo() throws CommandHistoryException, CommandException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
	    Command cutSelectedTextCommand = new CutSelectedTextCommand(engine);
        
        invoker.execute(insertCommand);
        
        engine.getSelection().setEndIndex(1);
        engine.getSelection().setBeginIndex(0);
        invoker.execute(cutSelectedTextCommand);
        
        assertEquals("ello world", engine.getBufferContents());
        invoker.undo();
        assertEquals("Hello world", engine.getBufferContents());
        invoker.undo();
        assertEquals("", engine.getBufferContents());
	}
	
	@Test
	void nullCommandThrowsException() throws CommandException{
		Command insertCommand = null;
		
		assertThrows(CommandException.class, ()->{
			invoker.execute(insertCommand);
		});   
	}
	
	@Test
	void commandHistoryAndCareTakerHaveDifferenteSizes() throws CommandHistoryException, CommandException, SelectionStateException {
		Command insertCommand = new InsertCommand(engine, "Hello world");
	    Command cutSelectedTextCommand = new CutSelectedTextCommand(engine);
	    
	    invoker.execute(insertCommand);
	    engine.insert("Other content"); //Execute a command not through the invoker
	    
//		assertThrows(CommandHistoryException.class, ()->{
//			invoker.replay();
//		});
	}
	
	@Test
	void undo_withEmptyCommandHistory_throwsException() throws CommandHistoryException{
		Command insertCommand = new InsertCommand(engine, "Hello world");
		
		assertThrows(CommandHistoryException.class, ()->{
			invoker.undo();
		});
	}
	
	
}
