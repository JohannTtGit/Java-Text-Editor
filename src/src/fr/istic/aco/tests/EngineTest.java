package fr.istic.aco.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.aco.Selection.Selection;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class EngineTest {

    private Engine engine;

    @BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }
    
    @Test
    void setBuffer() {
    	engine = new EngineImpl();
        assertEquals("",engine.getBufferContents());
    }
    
    @Test
    @DisplayName("Buffer must be empty after initialisation")
    void getSelection() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(),selection.getBeginIndex());
        assertEquals("",engine.getBufferContents());
    }
    
    @Test
    void insertTest() {
    	engine.insert("Hello world");
    	engine.insert("abc");
        assertEquals("abcHello world", engine.getBufferContents());
        engine.getSelection().setEndIndex(1);
        engine.getSelection().setBeginIndex(1);
    	engine.insert("123");
    	assertEquals("a123bcHello world", engine.getBufferContents());
    }

    @Test
    void getBufferContents() {
        engine.insert("hello world");
        assertEquals("hello world", engine.getBufferContents());
        
        engine.insert("test:");
        assertEquals("test:hello world", engine.getBufferContents());
        
    }
    
    @Test
    void setBeginAndEndIndex() {
    	engine.insert("123");
    	engine.getSelection().setEndIndex(3);
    	engine.getSelection().setBeginIndex(1);
    	assertEquals(3, engine.getSelection().getEndIndex());
    	assertEquals(1, engine.getSelection().getBeginIndex());
    }
    
    @Test
    void setBeginAndEndIndexExceptionTest() {
    	
    	Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
    		engine.getSelection().setEndIndex(3);
        });
    	
    	Exception exception2 = assertThrows(IndexOutOfBoundsException.class, () -> {
    		engine.getSelection().setBeginIndex(4);
        });
    }

    @Test
    void copySelectedText() {
    	engine.insert("hello world");
    	engine.getSelection().setEndIndex(3);
    	engine.getSelection().setBeginIndex(1);
    	engine.copySelectedText();
    	
    	assertEquals("el", engine.getClipboardContents());
    }
    
    @Test
    void getClipboardContents() {
    	engine.insert("hello world");
    	engine.getSelection().setEndIndex(3);
    	engine.getSelection().setBeginIndex(1);
    	engine.copySelectedText();
    	
    	assertEquals("el", engine.getClipboardContents());
    }

    @Test
    void cutSelectedText() {
    	engine.insert("hello world");
    	engine.getSelection().setEndIndex(3);
    	engine.getSelection().setBeginIndex(1);
    	engine.cutSelectedText();

    	assertEquals("el", engine.getClipboardContents());
    	assertEquals("hlo world", engine.getBufferContents());
    }
    

    @Test
    void pasteClipboard() {
    	engine.insert("abcdef");
    	engine.getSelection().setEndIndex(3);
    	engine.getSelection().setBeginIndex(1);
    	engine.copySelectedText(); //bc is selected
    	assertEquals("bc", engine.getClipboardContents());
    	assertEquals(1, engine.getSelection().getBeginIndex());
    	assertEquals(3, engine.getSelection().getEndIndex());

    	engine.pasteClipboard();
    	assertEquals("abcdef", engine.getBufferContents());
    }	
    
    @Test
    void delete() {
    	engine.insert("abcdef");
    	engine.getSelection().setEndIndex(4);
    	engine.getSelection().setBeginIndex(1);
    	engine.delete();
    	assertEquals("aef", engine.getBufferContents());
    }
    
    	
    	
  
}
