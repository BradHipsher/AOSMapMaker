package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static main.drawables.MapArea.*;
import static main.editRes.EditorResources.*;

import main.editRes.EditorResources;
import main.gui.BFrame;

public class Editor {
	
	private final static int[] UNPRINTABLE_MODES = {
			MODE_ID_1X2H	
	};
	
	private boolean transposed = true;
	private int mode = MODE_DEFAULT;
	
	private BFrame frame;

	
	
	public Editor() {
		setFrame(new BFrame(this));
	}
	
	
	
	public void start() {
		frame.setVisible(true);
	}
	
	
	public void saveMapAsPNG() {
		// TODO Doesn't print correctly for multiple page modes.
		BufferedImage bufferedImage = new BufferedImage(PAPER_WIDTH,PAPER_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		if (isTransposed()) bufferedImage = new BufferedImage(PAPER_HEIGHT,PAPER_WIDTH, BufferedImage.TYPE_INT_ARGB);
		getFrame().getPanel().getMapPanel().getMapArea().printablePaint(bufferedImage.createGraphics(),true);
		try {
			ImageIO.write(bufferedImage, "PNG", new File(Main.SAVE_PNG_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void transpose() {
		setTransposed(! isTransposed());
		frame.getPanel().getMapPanel().getMapArea().repaint();
	}
	
	public void changeMode() {
		setMode(EditorResources.queryModeDialog(frame));
		
	}
	
	public void passNewModeDown(int mode) {
		frame.getPanel().getMapPanel().getMapArea().newMode(mode);
	}
	
	private static boolean checkPrintability(int mode) {
		boolean ans = true;
		for (int uMode : UNPRINTABLE_MODES) {
			if (uMode == mode) ans = false;
		}
		return ans;
	}


	
	// Getters & Setters
	public BFrame getFrame() {
		return frame;
	}

	public void setFrame(BFrame frame) {
		this.frame = frame;
	}

	public boolean isTransposed() {
		return transposed;
	}

	public void setTransposed(boolean transposed) {
		this.transposed = transposed;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
		passNewModeDown(mode);
		frame.getPanel().getInteractPanel().getPrint().setEnabled(checkPrintability(mode));
	}

}
