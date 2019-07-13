package main.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import main.Editor;

public class BFrame extends JFrame{

	private static final long serialVersionUID = -8647078698486323377L;
	
	private Editor editor;
	private BPanel panel;
	
	public final static int FRAME_HEIGHT = 1400;
	public final static int FRAME_WIDTH = 2554;

	public BFrame(Editor editor) {
		setEditor(editor);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setUndecorated(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPanel(new BPanel(this));
		add(getPanel());
		pack();
	}
	
	
	
	// Getters & Setters
	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public BPanel getPanel() {
		return panel;
	}

	public void setPanel(BPanel panel) {
		this.panel = panel;
	}

}
