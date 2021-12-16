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
        this.undoCommand = new UndoCommand(engine, undoManager);
        this.deleteCommand = new DeleteCommand(engine, recorder, undoManager);
        invoker.addCommandToInvoker("insert", insertCommand);
        invoker.addCommandToInvoker("undo", undoCommand);
        invoker.addCommandToInvoker("delete", undoCommand);
		
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
		
		selectBtn.addActionListener(this);
		selectBtn.addActionListener(this);
		selectBtn.addActionListener(this);
		selectBtn.addActionListener(this);
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
	public void keyTyped(KeyEvent e) {
		
		System.out.println(KeyEvent.VK_BACK_SPACE);
		System.out.println(e.getKeyCode());
		
		if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
			invoker.setContentToInsert(String.valueOf(e.getKeyChar()));
			try {
				invoker.play("insert");
			}
			catch (CommandException e1) {
				e1.printStackTrace();
			}
			
			//Set the "cursor selection" for each type
			int currentPosition = textArea.getCaretPosition();
			engine.getSelection().setEndIndex(currentPosition);
			engine.getSelection().setBeginIndex(currentPosition);
		}
		
		//Set the "cursor selection" if the delete key is pressed
		//Execute the DeleteCommand
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			try {
				System.out.println("hey");
				engine.getSelection().setEndIndex(textArea.getCaretPosition()+1);
				engine.getSelection().setBeginIndex(textArea.getCaretPosition());
				invoker.play("delete");
				textArea.setText(engine.getBufferContents() + "<3");
			}
			catch (CommandException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(engine.getBufferContents() == "") {
			undoBtn.setEnabled(false);
		}
		
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
