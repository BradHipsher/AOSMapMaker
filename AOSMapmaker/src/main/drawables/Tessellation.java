package main.drawables;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.editRes.EditorResources;

import static main.drawables.MapArea.*;

public class Tessellation {

	private MapArea mapArea;

	private ArrayList<Hex> hexes;
	private int selectedHexID; 	



	public Tessellation(MapArea mapArea) {
		setMapArea(mapArea);
		setHexes(new ArrayList<Hex>());
		setSelectedHexID(-1);
		tesselate(-1);
	}



	public void tesselate(int pageNum) {
		
		// if pageNum = -1, then it's being drawn for the program
		// if pageNum > -1, then it's drawing a page for the print.

		// ASSUMES THAT YOU HAVE AT LEAST 2 ROWS!!!
		// ASSUMES that Row 1 and Row 2 are only 1
		// Tranposition means that L->R becomes T->B and vice versa
		// All that's happening is that x and y pixel drawings become y, x
		// Variable names will remain the same, obviously.
		// Text on hexes should maintain orientation? // TODO
		// Mountain drawings may need to back out transpostion on Hex file // TODO


		ArrayList<String> hexTypes = new ArrayList<String>();

		if (hexes.size() > 0) { // Saves the types
			hexTypes = new ArrayList<String>();
			for(Hex hex : hexes) {
				hexTypes.add(hex.getTileType());
			}
		}

		wipeHexes();
		
		int localMode = mapArea.getPanel().getPanel().getFrame().getEditor().getMode();

		int tempHexesWidth1 = EditorResources.W1W2H[localMode][0]; // w2 -- USE dimensions.xslx (swapped row 1 and row 2)
		int tempHexesWidth2 = EditorResources.W1W2H[localMode][1]; // w1 -- USE dimensions.xslx (swapped row 1 and row 2)
		int tempHexesHeight = EditorResources.W1W2H[localMode][2]; // h -- USE dimensions.xslx

		int maxHexesWidth = Math.max(tempHexesWidth1, tempHexesWidth2);
		int minHexesWidth = Math.min(tempHexesWidth1, tempHexesWidth2);

		int totalHexesWidth = tempHexesWidth1 * Hex.HEX_D_I + (minHexesWidth - maxHexesWidth + 1) * Hex.HEX_R_I;
		int totalHexesHeight = Hex.HEX_A*2 + (tempHexesHeight - 1) * Hex.HEX_H_I;

		int centerAlignOffsetX = (mapArea.getPaperWidth() - totalHexesWidth) / 2;
		int centerAlignOffsetY = (mapArea.getPaperHeight() - totalHexesHeight) / 2;

		double x = tempHexesWidth1 - tempHexesWidth2;
		int row2Sign = (int) (-Math.pow(x, 2) + x + 1); // Input {-1, 0, 1} --> {-1, 1, 1}  || -(x^2) + x + 1

		int mapAreaOffsetX = 0;
		int mapAreaOffsetY = 0;
		if(pageNum == -1) {
			mapAreaOffsetX = mapArea.getBoundaryX();
			mapAreaOffsetY = mapArea.getBoundaryY();
		}
		
		int pageNumberOffsetX = 0;
		int pageNumberOffsetY = 0;
		if (pageNum > -1) {
			pageNumberOffsetX = (pageNum % EditorResources.PAGE_SCALES_W_H[localMode][0]) * PAPER_WIDTH * (-1);
			pageNumberOffsetY = ((pageNum / EditorResources.PAGE_SCALES_W_H[localMode][0]) % EditorResources.PAGE_SCALES_W_H[localMode][1]) * PAPER_HEIGHT  * (-1);
		}
		
		int loopID = 0;

		if(mapArea.getPanel().getPanel().getFrame().getEditor().isTransposed()) {
			for(int i = 0; i < tempHexesHeight; i ++) {
				int tempHexesWidth = tempHexesWidth1;
				if (i % 2 == 1) tempHexesWidth = tempHexesWidth2;
				for(int j = 0; j < tempHexesWidth; j ++) {
					if (hexes.size() == loopID) hexTypes.add(EMPTY);
					Hex hex = new Hex(
							this, 
							pageNumberOffsetY + centerAlignOffsetY + mapAreaOffsetY + Hex.HEX_A + i*Hex.HEX_H_I,
							pageNumberOffsetX + centerAlignOffsetX + mapAreaOffsetX + Hex.HEX_D_I/2 + j*Hex.HEX_D_I + Hex.HEX_R_I*(i % 2)*row2Sign, 
							hexTypes.get(loopID),
							1);
					hexes.add(hex);
					loopID ++;
				}
			}
		} else {
			for(int i = 0; i < tempHexesHeight; i ++) {
				int tempHexesWidth = tempHexesWidth1;
				if (i % 2 == 1) tempHexesWidth = tempHexesWidth2;
				for(int j = 0; j < tempHexesWidth; j ++) {
					if (hexes.size() == loopID) hexTypes.add(EMPTY);
					Hex hex = new Hex(
							this, 
							pageNumberOffsetX + centerAlignOffsetX + mapAreaOffsetX + Hex.HEX_D_I/2 + j*Hex.HEX_D_I + Hex.HEX_R_I*(i % 2)*row2Sign, 
							pageNumberOffsetY + centerAlignOffsetY + mapAreaOffsetY + Hex.HEX_A + i*Hex.HEX_H_I,
							hexTypes.get(loopID),
							0);
					hexes.add(hex);
					loopID ++;
				}
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
	
	public void drawWaterBorder(Graphics2D g2d) {
		for(Hex hex : hexes) {
			if(hex.getTileType().equals(WATER)) hex.drawWaterBorder(g2d);
		}
	}
	
	public void drawTileBorder(Graphics2D g2d) {
		for(Hex hex : hexes) {
			if (hex.getTileType().equals(LAND)) hex.drawTileBorder(g2d);
			if (hex.getTileType().equals(RIVER)) hex.drawTileBorder(g2d);
			if (hex.getTileType().equals(MOUNTAIN)) hex.drawTileBorder(g2d);
			if (hex.getTileType().equals(TOWN)) hex.drawTileBorder(g2d);
			if (hex.getTileType().equals(CITY)) hex.drawTileBorder(g2d);		
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
		Hex selectedHex;
		String s = EMPTY;
		if ((selectedHex = getSelectedHex()) != null) s = selectedHex.getTileType();
		return s;
	}

	public Hex getSelectedHex() {
		try {
			return hexes.get(getSelectedHexID());
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
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
	
	public void wipeHexes() {
		hexes = new ArrayList<Hex>();
	}
	
	public void deselectHex() {
		setSelectedHexID(-1);
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
