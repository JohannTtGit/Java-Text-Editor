package fr.istic.aco.GUI;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.DeleteCommand;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Commands.UndoCommand;
import fr.istic.aco.Commands.setBeginIndexCommand;
import fr.istic.aco.Commands.setEndIndexCommand;
import fr.istic.aco.Exceptions.CommandException;
import fr.istic.aco.Recorder.Recorder;
import fr.istic.aco.Recorder.RecorderImpl;
import fr.istic.aco.Undo.UndoManager;
import fr.istic.aco.Undo.UndoManagerImpl;
import fr.istic.aco.editor.Engine;
import fr.istic.aco.editor.EngineImpl;

public class GuiDefinition implements KeyListener, ActionListener {
	
	private Engine engine;
	private Invoker invoker;
	private Recorder recorder;
	private UndoManager undoManager;
	
	Command insertCommand;
	Command setBeginIndex;
	Command setEndIndex;
	Command undoCommand;
	Command deleteCommand;
	
	private JFrame frame;
	private JPanel panel;
	private JTextArea textArea;
	private JButton selectBtn, copyBtn, cutBtn, pastBtn, undoBtn;
	
	public GuiDefinition() {
		this.engine = new EngineImpl();
		this.engine = new EngineImpl();
        this.invoker = new InvokerImpl();
        this.recorder = new RecorderImpl();
        this.undoManager = new UndoManagerImpl();
        
        this.insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
        this.setBeginIndex = new setBeginIndexCommand(engine, invoker, recorder, undoManager);
        this.setEndIndex = new setEndIndexCommand(engine, invoker, recorder, undoManager);
        this.undoCommand = new UndoCommand(engine, undoManager);
        this.deleteCommand = new DeleteCommand(engine, recorder, undoManager);
        invoker.addCommandToInvoker("insert", insertCommand);
        invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
        invoker.addCommandToInvoker("setEndIndex", setEndIndex);
        invoker.addCommandToInvoker("undo", undoCommand);
        invoker.addCommandToInvoker("delete", deleteCommand);
		
        this.frame = new JFrame();
		this.panel = new JPanel();
		this.textArea = new JTextArea(40, 60);
		textArea.addKeyListener(this);
		
		this.frame.setContentPane(panel);
		panel.add(textArea);
		
		panel.add(new JLabel("Buffer"));
		
		
		//Buttons definition
		this.selectBtn = new JButton("Select");
		this.copyBtn = new JButton("Copy");
		this.cutBtn = new JButton("Cut");
		this.pastBtn = new JButton("Past");
		this.undoBtn = new JButton("Undo");
		
		selectBtn.addActionListener(this); selectBtn.setEnabled(false);
		copyBtn.addActionListener(this); copyBtn.setEnabled(false);
		cutBtn.addActionListener(this); cutBtn.setEnabled(false);
		pastBtn.addActionListener(this); pastBtn.setEnabled(false);
		undoBtn.addActionListener(this); undoBtn.setEnabled(false);
		
		panel.add(selectBtn);
		panel.add(copyBtn);
		panel.add(cutBtn);
		panel.add(pastBtn);
		panel.add(undoBtn);
		
		this.frame.setTitle("Test");
		this.frame.setSize(750,780);
		this.frame.setVisible(true);
	}
	


	@Override
	public void keyPressed(KeyEvent e) {
		
		if(engine.getBufferContents() != "") {
			undoBtn.setEnabled(true);
			undoBtn.setEnabled(true);
		}
		
		//Classical insertion
		if(Character.isLetter(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_SPACE || Character.isDigit(e.getKeyChar())) {
			
			//Update the textArea content with the buffer content because
			// we only want to use the TextEditor features
			this.textArea.setText(engine.getBufferContents());
			
			invoker.setContentToInsert(String.valueOf(e.getKeyChar()));
			
			int currentPosition = textArea.getCaretPosition()+1;
			
			try {
				invoker.play("insert");
				
				//Replace cursor at the end, after each insert Command
				invoker.setEndIndex(currentPosition);
				invoker.setBeginIndex(currentPosition);
				invoker.play("setEndIndex");
				invoker.play("setBeginIndex");
			}
			catch (CommandException e1) {
				e1.printStackTrace();
			}
		}
	
		//Press BACK SPACE key -> Delete
		else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			
			invoker.setBeginIndex(engine.getSelection().getBeginIndex()-1);
			
			try {
				invoker.play("setBeginIndex");
				invoker.play("delete");
			}
			catch (CommandException e1) {
				e1.printStackTrace();
			}
			
			textArea.setText(engine.getBufferContents());
			
			//Move the cursor to enable a new delete action
			invoker.setEndIndex(engine.getSelection().getEndIndex()-1);
			
			try {
				invoker.play("setEndIndex");
			}
			catch (CommandException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == undoBtn) {
			try {
				invoker.play("undo");
			}
			catch (CommandException e1) {
				e1.printStackTrace();
			}
			
			textArea.setText(engine.getBufferContents());
		}
	
	
	}
}
