package main.gui;

import static main.gui.BFrame.*;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.drawables.MapArea;

public class MapPanel extends JPanel{
	
	private static final long serialVersionUID = -6321115478950854427L;
	public final static int PANEL_HEIGHT = FRAME_HEIGHT;
	public final static int PANEL_WIDTH = FRAME_WIDTH - 500;
	
	private BPanel panel;
	private MapArea mapArea;
	
	public MapPanel(BPanel panel) {
		setPanel(panel);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setMapArea(new MapArea(this));
		add(getMapArea());
	}
	
	
	
	// Getters & Setters
	public BPanel getPanel() {
		return panel;
	}

	public void setPanel(BPanel panel) {
		this.panel = panel;
	}

	public MapArea getMapArea() {
		return mapArea;
	}

	public void setMapArea(MapArea mapArea) {
		this.mapArea = mapArea;
	}


}
