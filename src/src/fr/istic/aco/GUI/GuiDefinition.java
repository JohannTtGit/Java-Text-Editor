package fr.istic.aco.GUI;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import fr.istic.aco.Commands.Command;
import fr.istic.aco.Commands.CopySelectedTextCommand;
import fr.istic.aco.Commands.CutSelectedTextCommand;
import fr.istic.aco.Commands.DeleteCommand;
import fr.istic.aco.Commands.InsertCommand;
import fr.istic.aco.Commands.Invoker;
import fr.istic.aco.Commands.InvokerImpl;
import fr.istic.aco.Commands.PasteClipboardCommand;
import fr.istic.aco.Commands.ReplayCommand;
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
	Command cutCommand;
	Command pastCommand;
	Command copyCommand;
	Command replayCommand;
	
	private JFrame frame;
	private JPanel panel;
	private JTextArea textArea;
	private JButton selectBtn, copyBtn, cutBtn, pastBtn, startRecordBtn, stopRecordBtn, replayBtn;
	private JSpinner beginIndexSpinner;
	private JSpinner endIndexSpinner;
	
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
        this.cutCommand = new CutSelectedTextCommand(engine, recorder, undoManager);
        this.pastCommand = new PasteClipboardCommand(engine, recorder, undoManager);
        this.copyCommand = new CopySelectedTextCommand(engine, recorder, undoManager);
        this.replayCommand = new ReplayCommand(recorder);
        invoker.addCommandToInvoker("insert", insertCommand);
        invoker.addCommandToInvoker("setBeginIndex", setBeginIndex);
        invoker.addCommandToInvoker("setEndIndex", setEndIndex);
        invoker.addCommandToInvoker("undo", undoCommand);
        invoker.addCommandToInvoker("delete", deleteCommand);
        invoker.addCommandToInvoker("cut", cutCommand);
        invoker.addCommandToInvoker("past", pastCommand);
        invoker.addCommandToInvoker("copy", copyCommand);
        invoker.addCommandToInvoker("replay", replayCommand);
		
        this.frame = new JFrame();
		this.panel = new JPanel();
		this.textArea = new JTextArea(40, 60);
		textArea.addKeyListener(this);
		
		this.frame.setContentPane(panel);
		panel.add(textArea);
		
		panel.add(new JLabel("Buffer"));
		panel.add(new JLabel(""));
		
		
		//Buttons definition
		this.selectBtn = new JButton("Select");
		this.copyBtn = new JButton("Copy");
		this.cutBtn = new JButton("Cut");
		this.pastBtn = new JButton("Past");
		this.startRecordBtn = new JButton("Start recording");
		this.stopRecordBtn = new JButton("Stop recording");
		this.replayBtn = new JButton("Replay");
		this.beginIndexSpinner = new JSpinner();
		this.endIndexSpinner = new JSpinner();
		
		selectBtn.addActionListener(this);
		copyBtn.addActionListener(this); copyBtn.setEnabled(false);
		cutBtn.addActionListener(this); cutBtn.setEnabled(false);
		pastBtn.addActionListener(this); pastBtn.setEnabled(false);
		startRecordBtn.addActionListener(this);
		stopRecordBtn.addActionListener(this); stopRecordBtn.setEnabled(false);
		replayBtn.addActionListener(this); replayBtn.setEnabled(false);
		
		panel.add(beginIndexSpinner);
		panel.add(endIndexSpinner);
		panel.add(selectBtn);
		panel.add(copyBtn);
		panel.add(cutBtn);
		panel.add(pastBtn);
		panel.add(startRecordBtn);
		panel.add(stopRecordBtn);
		panel.add(replayBtn);
		
		this.frame.setTitle("Simple Text Editor");
		this.frame.setSize(750,780);
		this.frame.setVisible(true);
	}
	


	@Override
	public void keyPressed(KeyEvent e) {
		
		if(engine.getBufferContents() != "") {
			startRecordBtn.setEnabled(true);
		}
		
		//Classical insertion
		if(Character.isLetter(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_SPACE || Character.isDigit(e.getKeyChar())) {
			
			//Update the textArea content with the buffer content because
			// we only want to use the TextEditor features
			this.textArea.setText(engine.getBufferContents());
			
			invoker.setContentToInsert(String.valueOf(e.getKeyChar()));
			
			int currentPosition = engine.getBufferContents().length()+1;
			
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
		
		if(e.getSource() == selectBtn) {
			
			//Get the wanted selection values
			int beginIndex = (int) beginIndexSpinner.getValue();
			int endIndex = (int) endIndexSpinner.getValue();
			
			invoker.setEndIndex(endIndex);
			invoker.setBeginIndex(beginIndex);
			
			try {
				invoker.play("setEndIndex");
				invoker.play("setBeginIndex");
			} catch (CommandException e1) { e1.printStackTrace();}
			
			copyBtn.setEnabled(true);
			cutBtn.setEnabled(true);
			pastBtn.setEnabled(true);
		}
		
		if(e.getSource() == cutBtn) {
			
			try {
				invoker.play("cut");
			} catch (CommandException e1) {e1.printStackTrace();}
			
			textArea.setText(engine.getBufferContents());
		}
		
		if(e.getSource() == pastBtn) {
			
			try {
				invoker.play("past");
			} catch (CommandException e1) {e1.printStackTrace();}
			
			System.out.println(engine.getBufferContents());
			textArea.setText(engine.getClipboardContents());
		}
		
		if(e.getSource() == copyBtn) {
			
			try {
				invoker.play("copy");
			} catch (CommandException e1) {e1.printStackTrace();}
		}
		
		if(e.getSource() == startRecordBtn) {
			recorder.start();
			stopRecordBtn.setEnabled(true);
		}
		
		if(e.getSource() == stopRecordBtn) {
			recorder.stop();
			replayBtn.setEnabled(true);
		}
		
		if(e.getSource() == replayBtn) {
			try {
				invoker.play("replay");
			} catch (CommandException e1) {e1.printStackTrace();}
			
			textArea.setText(engine.getBufferContents());
			System.out.println(engine.getBufferContents());
		}
	}
}
