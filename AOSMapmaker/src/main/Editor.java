package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static main.drawables.MapArea.*;

import main.gui.BFrame;

public class Editor {
	
	final public static int MODE_1X1V = 0;
	final public static int MODE_2X1V = 1;
	final public static int MODE_1X2V = 2;
	final public static int MODE_2X2V = 3;
	final public static int MODE_3X1V = 4;
	final public static int MODE_1X3V = 5;
	final public static int MODE_3X3V = 6;
	final public static int MODE_V_TO_H_ADDER = 100; 
	final public static int MODE_1X1H = MODE_V_TO_H_ADDER + MODE_1X1V;
	final public static int MODE_2X1H = MODE_V_TO_H_ADDER + MODE_2X1V;
	final public static int MODE_1X2H = MODE_V_TO_H_ADDER + MODE_1X2V;
	final public static int MODE_2X2H = MODE_V_TO_H_ADDER + MODE_2X2V;
	final public static int MODE_3X1H = MODE_V_TO_H_ADDER + MODE_3X1V;
	final public static int MODE_1X3H = MODE_V_TO_H_ADDER + MODE_1X3V;
	final public static int MODE_3X3H = MODE_V_TO_H_ADDER + MODE_3X3V;
	final public static int MODE_DEFAULT = MODE_1X1V;
	
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
		BufferedImage bufferedImage = new BufferedImage(BOUNDARY_WIDTH,BOUNDARY_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		if (isTransposed()) bufferedImage = new BufferedImage(BOUNDARY_HEIGHT,BOUNDARY_WIDTH, BufferedImage.TYPE_INT_ARGB);
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
	}

}
