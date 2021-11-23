package fr.istic.aco.editor;

import fr.istic.aco.Selection.Selection;
import fr.istic.aco.Selection.SelectionImpl;

/**
 * Implements the Engine that provides the user operations on the text editor
 * @author Niklas, Johann
 *
 */
public class EngineImpl implements Engine {
	
	private StringBuilder buffer; // contains the text editor content
	private StringBuilder clipboard; // stores a copy of a selection
	private Selection selection;
	
	public EngineImpl() {
		this.setBuffer(new StringBuilder());
		setClipboard(new StringBuilder());
		setSelection(new SelectionImpl(this.buffer));
	}
	
	/**
	 * sets the clipboard content
	 * @param clipboard the content to be saved in the clipboard
	 */
    private void setClipboard(StringBuilder clipboard) {
		this.clipboard = clipboard;
	}

    @Override
    public Selection getSelection() {
        return selection;
    }

    @Override
    public String getBufferContents() {
    	return buffer.toString();
    }

    @Override
    public String getClipboardContents() {
        return clipboard.toString();
    }

    @Override
    public void cutSelectedText() {
    	int start = this.getSelection().getBeginIndex();
    	int end = this.getSelection().getEndIndex();
        copySelectedText();
    	this.setBuffer(this.getBuffer().delete(start, end));
    }

    @Override
    public void copySelectedText() {
    	int start = this.getSelection().getBeginIndex();
    	int end = this.getSelection().getEndIndex();
        this.clipboard = new StringBuilder(this.getBuffer().substring(start, end));
    }

    @Override
    public void pasteClipboard() {
        this.insert(this.getClipboardContents());
    }

    @Override
    public void insert(String s) {
    	int start= this.getSelection().getBeginIndex();
    	int end= this.getSelection().getEndIndex();

    	if(start < end) {
    		this.delete();
    	}
    	this.setBuffer(this.getBuffer().insert(this.getSelection().getBeginIndex(), s));
    }

    @Override
    public void delete() {
    	int start= this.getSelection().getBeginIndex();
    	int end= this.getSelection().getEndIndex();
    	
    	this.setBuffer(this.getBuffer().delete(start, end));
    }
    
    /**
     * 
     * @return the attribute buffer
     */
    private StringBuilder getBuffer() {
		return buffer;
	}
    
    /**
     * Update the attribute buffer
     * @param buffer the contents to be saved in the buffer
     */
	private void setBuffer(StringBuilder buffer) {
		this.buffer = buffer;
	}
	
	/**
	 * 
	 * @param selection the selection to be set
	 */
	private void setSelection(Selection selection) {
		this.selection = selection;
	}

	@Override
	public void clearBuffer() {
		this.buffer = new StringBuilder();
	}
	
}
