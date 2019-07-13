package main.drawables;

import java.awt.Graphics2D;
import java.util.ArrayList;

import static main.drawables.MapArea.*;

public class Tessellation {

	private MapArea mapArea;
	private int mode;

	private ArrayList<Hex> hexes;
	private int selectedHexID; 	
	

	public Tessellation(MapArea mapArea) {
		setMapArea(mapArea);
		setHexes(new ArrayList<Hex>());
		setSelectedHexID(-1);
	}

	public Tessellation(MapArea mapArea, int mode) {
		this(mapArea);
		setMode(mode);
		tesselate(true);
	}
	
	
	
	public void tesselate(boolean withMapAreaOffsets) {
		
		// ASSUMES THAT YOU HAVE AT LEAST 2 ROWS!!!
		// ASSUMES that Row 1 and Row 2 are only 1
		// ASSUMES Edge-Vertical Orientation
		// TODO allow flipping Orientation (I think just swap i and j; names wouldn't change -- just know you're transposed)
		// ASSUMES MODE_1X1H = 6&5 wide, 5 tall
		// TODO Change Based on Mode
		
		ArrayList<String> hexTypes = new ArrayList<String>();
		
		if (hexes.size() > 0) { // Saves the types
			hexTypes = new ArrayList<String>();
			for(Hex hex : hexes) {
				hexTypes.add(hex.getTileType());
			}
		}
		
		setHexes(new ArrayList<Hex>());
		
		int tempHexesWidth1 = 7; // w2 -- USE dimensions.xslx (swapped row 1 and row 2)
		int tempHexesWidth2 = 7; // w1 -- USE dimensions.xslx (swapped row 1 and row 2)
		int tempHexesHeight = 6; // h -- USE dimensions.xslx
		
		int maxHexesWidth = Math.max(tempHexesWidth1, tempHexesWidth2);
		int minHexesWidth = Math.min(tempHexesWidth1, tempHexesWidth2);

		int totalHexesWidth = tempHexesWidth1 * Hex.HEX_D_I + (minHexesWidth - maxHexesWidth + 1) * Hex.HEX_R_I;
		int totalHexesHeight = Hex.HEX_A*2 + (tempHexesHeight - 1) * Hex.HEX_H_I;
		
		int centerAlignOffsetX = (BOUNDARY_WIDTH - totalHexesWidth) / 2;
		int centerAlignOffsetY = (BOUNDARY_HEIGHT - totalHexesHeight) / 2;
		
		double x = tempHexesWidth1 - tempHexesWidth2;
		int row2Sign = (int) (-Math.pow(x, 2) + x + 1); // Input {-1, 0, 1} --> {-1, 1, 1}  || -(x^2) + x + 1
		
		int mapAreaOffsetX = 0;
		int mapAreaOffsetY = 0;
		if(withMapAreaOffsets) {
			mapAreaOffsetX = mapArea.getBoundaryX();
			mapAreaOffsetY = mapArea.getBoundaryY();
		}

		int loopID = 0;
		
		for(int i = 0; i < tempHexesHeight; i ++) {
			System.out.println(hexes.size());
			int tempHexesWidth = tempHexesWidth1;
			if (i % 2 == 1) tempHexesWidth = tempHexesWidth2;
			for(int j = 0; j < tempHexesWidth; j ++) {
				if (hexes.size() == loopID) hexTypes.add(EMPTY);
				Hex hex = new Hex(
						this, 
						centerAlignOffsetX + mapAreaOffsetX + Hex.HEX_D_I/2 + j*Hex.HEX_D_I + Hex.HEX_R_I*(i % 2)*row2Sign, 
						centerAlignOffsetY + mapAreaOffsetY + Hex.HEX_A + i*Hex.HEX_H_I,
						hexTypes.get(loopID));
				hexes.add(hex);
				loopID ++;
			}
		}
		
		
		
	}

	
	public void drawGrid(Graphics2D g2d) { 
		for(Hex hex : hexes) {
				hex.drawGrid(g2d);
		}
	}
	
	public void drawType(Graphics2D g2d) { 
		for(Hex hex : hexes) {
				hex.drawType(g2d);
		}
	}	
	public void drawSelected(Graphics2D g2d, int mouseX, int mouseY) { 
		int hexID = getSelectedHexID();
		if (hexID !=-1) hexes.get(hexID).drawSelected(g2d);
		
	}
	
	public void selectHex(int selectedX, int selectedY) {
		setSelectedHexID(findSelectedHex(selectedX,selectedY));
	}
	
	public String getSelectedHexType() {
		return hexes.get(getSelectedHexID()).getTileType();
	}
	
	public Hex getSelectedHex() {
		return hexes.get(getSelectedHexID());
	}
	
	
	private int findSelectedHex(int selectedX, int selectedY) {
		int ans = -1;
		ArrayList<Integer> multiValidID = new ArrayList<Integer>();
		ArrayList<Double> distances = new ArrayList<Double>();
		for (int i = 0; i < hexes.size(); i ++) {
			double distance;
			if ((distance = hexes.get(i).checkBounds(selectedX, selectedY))>-1) {
				multiValidID.add(i);
				distances.add(distance);
			}
		}
		if (multiValidID.size() > 0) ans = multiValidID.get(getIDOfMin(distances));
		return ans; 
	}
	
	
	
	
	// Static Methods
	public static int getIDOfMin(ArrayList<Double> al) {
		int id = -1;
		for (int i = 0; i < al.size(); i ++) {
			if (i==0) id = 0;
			if (al.get(i) < al.get(id)) id = i;
		}
		return id;
	}




	// Getters & Setters
	public MapArea getMapArea() {
		return mapArea;
	}

	public void setMapArea(MapArea mapArea) {
		this.mapArea = mapArea;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public ArrayList<Hex> getHexes() {
		return hexes;
	}

	public void setHexes(ArrayList<Hex> hexes) {
		this.hexes = hexes;
	}

	public int getSelectedHexID() {
		return selectedHexID;
	}

	public void setSelectedHexID(int i) {
		this.selectedHexID = i;
	}

}
