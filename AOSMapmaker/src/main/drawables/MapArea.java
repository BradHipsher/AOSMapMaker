package main.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import main.gui.BPanel;
import main.gui.MapPanel;

public class MapArea extends Component {

	private static final long serialVersionUID = -812071750922935541L;
	
	final public static int MODE_1X1H = 1;
	final public static int MODE_2X1H = 2;
	final public static int MODE_1X2H = 3;
	final public static int MODE_2X2H = 4;
	final public static int MODE_3X1H = 5;
	final public static int MODE_1X3H = 6;
	final public static int MODE_3X3H = 7;
	final public static int MODE_1X1V = 11;
	final public static int MODE_2X1V = 12;
	final public static int MODE_1X2V = 13;
	final public static int MODE_2X2V = 14;
	final public static int MODE_3X1V = 15;
	final public static int MODE_1X3V = 16;
	final public static int MODE_3X3V = 17;
	final public static int MODE_DEFAULT = MODE_1X1H;
	
	final public static int BOUNDARY_WIDTH = 1456; // paper_h w -- USE dimensions.xlsx
	final public static int BOUNDARY_HEIGHT = 1125; // paper_h h -- USE dimensions.xlsx
	
	final public static int BOUNDARY_X = 60;
	final public static int BOUNDARY_Y = 110;
	
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
	private int mode;
	
	Graphics2D g2d;
	
	int mouseX = -1;
	int mouseY = -1;
		
	public MapArea(MapPanel panel, int mode) {
		setpanel(panel);
		addMouseListener(new MapMouseListener(this));
		setMode(mode);
		setTessellation(new Tessellation(this, mode));
	}
	
	public MapArea(MapPanel panel) {
		this(panel, MODE_DEFAULT);
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
		g2d.fillRect(getBoundaryX(), getBoundaryY(), BOUNDARY_WIDTH, BOUNDARY_HEIGHT);
	}
	
	public void printablePaint(Graphics2D g2d, boolean retesselate) {
		Stroke s = null;
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
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mapArea.mouseClicked(e.getX(), e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	
	
	// Getters & Setters
	public MapPanel getpanel() {
		return panel;
	}

	public void setpanel(MapPanel panel) {
		this.panel = panel;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
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
	
	
}
