package main.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import static main.gui.BFrame.*;

import java.awt.Dimension;
import java.awt.FlowLayout;

public class BPanel extends JPanel{

	private static final long serialVersionUID = -5898726512969099844L;
	
	public final static int PANEL_HEIGHT = FRAME_HEIGHT;
	public final static int PANEL_WIDTH = FRAME_WIDTH;
	
	private BFrame frame;
	private InteractPanel interactPanel;
	private MapPanel mapPanel;
	
	public BPanel(BFrame frame) {
		setFrame(frame);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setInteractPanel(new InteractPanel(this));
		add(getInteractPanel());
		setMapPanel(new MapPanel(this));
		add(getMapPanel());
	}
	
	
	
	// Getters & Setters
	public BFrame getFrame() {
		return frame;
	}

	public void setFrame(BFrame frame) {
		this.frame = frame;
	}


	public MapPanel getMapPanel() {
		return mapPanel;
	}

	public void setMapPanel(MapPanel mapPanel) {
		this.mapPanel = mapPanel;
	}

	public InteractPanel getInteractPanel() {
		return interactPanel;
	}

	public void setInteractPanel(InteractPanel interactPanel) {
		this.interactPanel = interactPanel;
	}

}
