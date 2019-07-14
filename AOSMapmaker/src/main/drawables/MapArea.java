package main.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.editRes.EditorResources;
import main.gui.BPanel;
import main.gui.MapPanel;

public class MapArea extends Component {

	private static final long serialVersionUID = -812071750922935541L;
	
	final public static int PAPER_WIDTH = 1288; // paper_h w -- USE dimensions.xlsx
	final public static int PAPER_HEIGHT = 995; // paper_h h -- USE dimensions.xlsx
	
	private int paperWidth = PAPER_WIDTH;
	private int paperHeight = PAPER_HEIGHT;
	
	final public static int BOUNDARY_X = 20;
	final public static int BOUNDARY_Y = 20;
	
	private int boundaryX = BOUNDARY_X;
	private int boundaryY = BOUNDARY_Y;
	
	final public static String EMPTY = "(none)";
	final public static String WATER = "water";
	final public static String LAND = "land";
	final public static String RIVER = "river";
	final public static String MOUNTAIN = "mountain";
	final public static String TOWN = "town";
	final public static String CITY = "city";
	
	
	private MapPanel panel;
	private Tessellation tessellation;
	
	Graphics2D g2d;
	
	int mouseX = -1;
	int mouseY = -1;
			
	public MapArea(MapPanel panel) {
		setPanel(panel);
		addMouseListener(new MapMouseListener(this));
		setTessellation(new Tessellation(this));
	}

	
	
	public void paint(Graphics g) {
		g2d = (Graphics2D) g;
		prePrintablePaint(g2d);
		printablePaint(g2d, false);
		postPrintablePaint(g2d);
	}
	
	public void prePrintablePaint(Graphics2D g2d) {
		// Background
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, BPanel.PANEL_WIDTH, BPanel.PANEL_HEIGHT);
		
		// Map Boundaries (assume 8.5 by 11)
		g2d.setColor(Color.WHITE);
		if (panel.getPanel().getFrame().getEditor().isTransposed()) {
			g2d.fillRect(getBoundaryY(), getBoundaryX(), paperHeight, paperWidth);
		} else {
			g2d.fillRect(getBoundaryX(), getBoundaryY(), paperWidth, paperHeight);
		}
	}
	
	public void printablePaint(Graphics2D g2d, boolean retesselate) {
		Stroke s = null;
		tessellation.tesselate(true);
		if (retesselate) {
			s = g2d.getStroke();
			tessellation.tesselate(false);
			g2d.setStroke(new BasicStroke(2.0f));;
		}
		tessellation.drawGrid(g2d);
		tessellation.drawType(g2d);
		if (retesselate) {
			tessellation.tesselate(true);
			g2d.setStroke(s);
		}
	}
	
	public void postPrintablePaint(Graphics2D g2d) {
		tessellation.drawSelected(g2d, mouseX, mouseY);

	}
	
	public void newMode(int mode) {
		// wipe data
		tessellation.wipeHexes();
		paperWidth = PAPER_WIDTH * EditorResources.PAGE_SCALES_W_H[mode][0];
		paperHeight = PAPER_HEIGHT * EditorResources.PAGE_SCALES_W_H[mode][1];
		repaint();
	}
	


	
	public void mouseClicked(int mouseX, int mouseY) {
		tessellation.selectHex(mouseX,mouseY);
		panel.getPanel().getInteractPanel().refreshRadioButtons(tessellation.getSelectedHexType());
		System.out.println("Mouse Clicked at: " + mouseX + ", " + mouseY + " | type: " + tessellation.getSelectedHexType());
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		repaint();
	}
	

	
	// Internal Classes
	class MapMouseListener implements MouseListener {
		
		MapArea mapArea;
		
		public MapMouseListener(MapArea mapArea) {
			this.mapArea = mapArea;
		}
		
		// Override Methods
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {
			mapArea.mouseClicked(e.getX(), e.getY());
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}

	
	
	// Getters & Setters
	public MapPanel getPanel() {
		return panel;
	}

	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}

	public int getBoundaryX() {
		return boundaryX;
	}

	public void setBoundaryX(int boundaryX) {
		this.boundaryX = boundaryX;
	}

	public int getBoundaryY() {
		return boundaryY;
	}

	public void setBoundaryY(int boundaryY) {
		this.boundaryY = boundaryY;
	}

	public Tessellation getTessellation() {
		return tessellation;
	}

	public void setTessellation(Tessellation tessellation) {
		this.tessellation = tessellation;
	}

	public int getPaperWidth() {
		return paperWidth;
	}

	public void setPaperWidth(int paperWidth) {
		this.paperWidth = paperWidth;
	}

	public int getPaperHeight() {
		return paperHeight;
	}

	public void setPaperHeight(int paperHeight) {
		this.paperHeight = paperHeight;
	}
	
}
