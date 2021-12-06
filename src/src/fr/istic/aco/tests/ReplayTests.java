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
import fr.istic.aco.Memento.CareTakerImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

class ReplayTests {
	
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
	void replayUniqueInsert() throws CommandException {
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, caretaker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.setContentToInsert("Hello world");
		
		caretaker.start();
		invoker.play("insertCommand");
		caretaker.stop();
		
		Command replayCommand = new ReplayCommand(caretaker);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		invoker.play("replayCommand");
		
		assertEquals("Hello worldHello world", engine.getBufferContents());
	}
	
	@Test
	void replayInsertAndCutPaste() throws CommandException{
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, caretaker);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, caretaker);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, caretaker);
		Command cutSelectedTextCommand = new CutSelectedTextCommand(engine, caretaker);
		CommandGlobal pasteCommand = new PasteClipboardCommand(engine, caretaker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		invoker.addCommandToInvoker("cutSelectedTextCommand", cutSelectedTextCommand);
		invoker.addCommandToInvoker("pasteCommand", pasteCommand);
		
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		invoker.play("insertCommand");
		
		caretaker.start();
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("cutSelectedTextCommand");
		invoker.play("pasteCommand");
		
		caretaker.stop();
		
		assertEquals("Helworld", engine.getBufferContents());
		
		Command replayCommand = new ReplayCommand(caretaker);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		
		invoker.play("replayCommand");
		
		assertEquals("Helld", engine.getBufferContents());
	}
	
	@Test
	void replayInsertAndCopyPaste() throws CommandException{
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, caretaker);
		Command setBeginIndex = new setBeginIndexCommand(engine, invoker, caretaker);
		Command setEndIndex = new setEndIndexCommand(engine, invoker, caretaker);
		Command copySelectedTextCommand = new CopySelectedTextCommand(engine, caretaker);
		CommandGlobal pasteCommand = new PasteClipboardCommand(engine, caretaker);
		invoker.addCommandToInvoker("insertCommand", insertCommand);
		invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
		invoker.addCommandToInvoker("setEndIndex", setEndIndex);
		invoker.addCommandToInvoker("copySelectedTextCommand", copySelectedTextCommand);
		invoker.addCommandToInvoker("pasteCommand", pasteCommand);
		
		
		invoker.setContentToInsert("Hello world");
		invoker.setBeginIndex(0);
		invoker.setEndIndex(3);
		
		
		caretaker.start();
		
		invoker.play("insertCommand");
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("copySelectedTextCommand");
		invoker.play("pasteCommand");
		
		caretaker.stop();
		
		Command replayCommand = new ReplayCommand(caretaker);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		
		invoker.play("replayCommand");
		
		assertEquals("Hello worldlo world", engine.getBufferContents());
	}
	
	@Test
	void replayDelete() throws CommandException {
		Command deleteCommand = new DeleteCommand(engine, caretaker);
		CommandGlobal insertCommand = new InsertCommand(engine, invoker, caretaker);
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
		
		caretaker.start();
		
		invoker.play("setEndIndex");
		invoker.play("setBeginIndex");
		invoker.play("deleteCommand");
		
		caretaker.stop();
		
		Command replayCommand = new ReplayCommand(caretaker);
		invoker.addCommandToInvoker("replayCommand", replayCommand);
		
		invoker.play("replayCommand");
		
		assertEquals("world", engine.getBufferContents());
	}

}
