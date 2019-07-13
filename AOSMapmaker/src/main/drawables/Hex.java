package main.drawables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;

import static main.drawables.MapArea.*;

public class Hex extends Polygon {

	private static final long serialVersionUID = 5785025424345449731L;

	final static int HEX_R_I = 94; // Incircle Radius -- USE dimensions.xslx
	final static int HEX_H_I = 162; // Height of hex rows -- USE dimensions.xslx
	final static int HEX_A = 108; // Circumcircle Radius = Hex Edge -- USE dimensions.xslx 
	final static int HEX_D_I = HEX_R_I*2; // Incircle Diameter

	private Tessellation tessellation;
	private int centerX;
	private int centerY;
	private String tileType;



	public Hex(Tessellation tessellation, int centerX, int centerY, String tileType) {
		super(new int[] {centerX, centerX+HEX_R_I,centerX+HEX_R_I,centerX, centerX-HEX_R_I,centerX-HEX_R_I }, 
				new int[] {centerY-HEX_A, centerY-HEX_A/2, centerY+HEX_A/2, centerY+HEX_A, centerY+HEX_A/2, centerY-HEX_A/2}, 
				6);
		setTessellation(tessellation);
		setCenterX(centerX);
		setCenterY(centerY);
		setTileType(tileType);
	}

	
	

	public void drawGrid(Graphics2D g2d) {
		Color prevColor = g2d.getColor();

		g2d.setColor(Color.LIGHT_GRAY);
		g2d.drawPolygon(this);

		g2d.setColor(prevColor);
	}
	
	public void drawType(Graphics2D g2d) {
		Color prevColor = g2d.getColor();
		
		if (tileType.equals(WATER)) drawWater(g2d);
		if (tileType.equals(LAND)) drawLand(g2d);
		if (tileType.equals(RIVER)) drawRiver(g2d);
		if (tileType.equals(MOUNTAIN)) drawMountain(g2d);
		if (tileType.equals(TOWN)) drawTown(g2d);
		if (tileType.equals(CITY)) drawCity(g2d);
		
		g2d.setColor(prevColor);
	}
	
	
	public void drawSelected(Graphics2D g2d) {
		Color prevColor = g2d.getColor();

		g2d.setColor(Color.BLUE);
		g2d.drawPolygon(this);

		g2d.setColor(prevColor);
	}
	
	
	
	private void drawWater(Graphics2D g2d) {
		Color prevColor = g2d.getColor();

		g2d.setColor(Color.WHITE);
		g2d.fillPolygon(this);

		g2d.setColor(prevColor);
	}
	
	private void drawLand(Graphics2D g2d) {
		Color prevColor = g2d.getColor();

		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillPolygon(this);
		g2d.setColor(Color.BLACK);
		g2d.drawPolygon(this);

		g2d.setColor(prevColor);
	}
	
	private void drawRiver(Graphics2D g2d) { // TODO, make connect with other river hexes
		Color prevColor = g2d.getColor();
		
		drawLand(g2d);

		g2d.setColor(Color.LIGHT_GRAY);
		g2d.drawPolygon(this);
		g2d.setColor(Color.BLACK);
		g2d.drawPolygon(this);

		g2d.setColor(prevColor);
	}
	
	private void drawMountain(Graphics2D g2d) {
		Color prevColor = g2d.getColor();

		drawLand(g2d);
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillPolygon(new Triangle(centerX, centerY, 2*HEX_R_I/3));
		g2d.setColor(Color.BLACK);
		g2d.drawPolygon(this);

		g2d.setColor(prevColor);
	}
	
	private void drawTown(Graphics2D g2d) {
		Color prevColor = g2d.getColor();

		drawLand(g2d);
		
		g2d.setColor(Color.WHITE);
		g2d.fillOval(centerX - (HEX_R_I/2),centerY - (HEX_R_I/2), HEX_R_I,HEX_R_I);
		g2d.setColor(Color.BLACK);
		g2d.drawPolygon(this);

		g2d.setColor(prevColor);
	}
	
	private void drawCity(Graphics2D g2d) {
		Color prevColor = g2d.getColor();
		Font prevFont = g2d.getFont();

		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font(Font.SERIF, Font.BOLD, 40));
		g2d.drawString("city", centerX - 30, centerY);
		g2d.setColor(Color.BLACK);
		g2d.drawPolygon(this);

		g2d.setFont(prevFont);
		g2d.setColor(prevColor);
	}

	
	
	
	public double checkBounds(int x, int y) {
		double ans = Math.sqrt((Math.pow((double) centerX - x, 2) + Math.pow((double) centerY - y, 2)));
		if(ans > HEX_A) ans = -1;
		return ans;
	}

	
	public class Triangle extends Polygon {

		private static final long serialVersionUID = 8426351275146517009L;
		
		Triangle(int centerX, int centerY, int dim) {
			super(new int[] {centerX - dim/2, centerX, centerX + dim/2},
					new int[] {centerY + dim/3, centerY - dim/2, centerY + dim/3},
					3);
		}
		
	}


	// Getters & Setters
	public Tessellation getTessellation() {
		return tessellation;
	}

	public void setTessellation(Tessellation tessellation) {
		this.tessellation = tessellation;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}




	public String getTileType() {
		return tileType;
	}




	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

}
