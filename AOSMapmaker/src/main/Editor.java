package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static main.drawables.MapArea.*;

import main.gui.BFrame;

public class Editor {
	
	private BFrame frame;
	
	
	
	public Editor() {
		setFrame(new BFrame(this));
	}
	
	
	
	public void start() {
		frame.setVisible(true);
	}
	
	
	public void saveMapAsPNG() {
		BufferedImage bufferedImage = new BufferedImage(BOUNDARY_WIDTH,BOUNDARY_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		getFrame().getPanel().getMapPanel().getMapArea().printablePaint(bufferedImage.createGraphics(),true);
		try {
			ImageIO.write(bufferedImage, "PNG", new File(Main.SAVE_PNG_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	// Getters & Setters
	public BFrame getFrame() {
		return frame;
	}

	public void setFrame(BFrame frame) {
		this.frame = frame;
	}

}
