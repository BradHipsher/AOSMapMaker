package main.gui;

import static main.gui.BFrame.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import static main.drawables.MapArea.*;


public class InteractPanel extends JPanel {

	private static final long serialVersionUID = -7625961776531140571L;

	public final static int PANEL_HEIGHT = FRAME_HEIGHT;
	public final static int PANEL_WIDTH = 480;

	private BPanel panel;
	private JButton print, transpose, changeMode;
	private ButtonGroup radioButtons;
	private JRadioButton empty, water, land, river, mountain, town, city;


	public InteractPanel(BPanel panel) {
		setPanel(panel);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setLayout(new FlowLayout(FlowLayout.CENTER));

		// Tile Options
		radioButtons = new ButtonGroup();

		//TODO set Mnemonics!
		empty = new JRadioButton(EMPTY);
		empty.setActionCommand(EMPTY);
		empty.addActionListener((e) -> actionPerformed(e));
		empty.setMnemonic('e');
		empty.setSelected(true);
		radioButtons.add(empty);
		add(empty);

		water = new JRadioButton(WATER);
		water.setActionCommand(WATER);
		water.addActionListener((e) -> actionPerformed(e));
		water.setMnemonic('w');
		radioButtons.add(water);
		add(water);

		land = new JRadioButton(LAND);
		land.setActionCommand(LAND);
		land.addActionListener((e) -> actionPerformed(e));
		land.setMnemonic('l');
		radioButtons.add(land);
		add(land);

		river = new JRadioButton(RIVER);
		river.setActionCommand(RIVER);
		river.addActionListener((e) -> actionPerformed(e));
		river.setMnemonic('r');
		radioButtons.add(river);
		add(river);

		mountain = new JRadioButton(MOUNTAIN);
		mountain.setActionCommand(MOUNTAIN);
		mountain.addActionListener((e) -> actionPerformed(e));
		mountain.setMnemonic('m');
		radioButtons.add(mountain);
		add(mountain);

		town = new JRadioButton(TOWN);
		town.setActionCommand(TOWN);
		town.addActionListener((e) -> actionPerformed(e));
		town.setMnemonic('t');
		radioButtons.add(town);
		add(town);

		city = new JRadioButton(CITY);
		city.setActionCommand(CITY);
		city.addActionListener((e) -> actionPerformed(e));
		city.setMnemonic('c');
		radioButtons.add(city);
		add(city);

		// ChangeMode Button
		changeMode = new JButton("Change Mode");
		changeMode.addActionListener((e) -> changeMode());
		add(changeMode);

		// Transpose Button
		transpose = new JButton("");
		setTransposeButtonName();
		transpose.addActionListener((e) -> transpose());
		add(transpose);

		// Print Button
		print = new JButton("PRINT");
		print.addActionListener((e) -> printToPNG());
		add(print);
	}



	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getActionCommand().equals(EMPTY)) panel.getMapPanel().getMapArea().getTessellation().getSelectedHex().setTileType(EMPTY);
			if (e.getActionCommand().equals(WATER)) panel.getMapPanel().getMapArea().getTessellation().getSelectedHex().setTileType(WATER);
			if (e.getActionCommand().equals(LAND)) panel.getMapPanel().getMapArea().getTessellation().getSelectedHex().setTileType(LAND);		
			if (e.getActionCommand().equals(RIVER)) panel.getMapPanel().getMapArea().getTessellation().getSelectedHex().setTileType(RIVER);		
			if (e.getActionCommand().equals(MOUNTAIN)) panel.getMapPanel().getMapArea().getTessellation().getSelectedHex().setTileType(MOUNTAIN);	
			if (e.getActionCommand().equals(TOWN)) panel.getMapPanel().getMapArea().getTessellation().getSelectedHex().setTileType(TOWN);	
			if (e.getActionCommand().equals(CITY)) panel.getMapPanel().getMapArea().getTessellation().getSelectedHex().setTileType(CITY);	
			panel.getMapPanel().getMapArea().repaint();
		} catch (NullPointerException x) {
			
		}
		
	}

	public void printToPNG() {
		getPanel().getFrame().getEditor().saveMapAsPNG();
	}

	public void transpose() {
		panel.getFrame().getEditor().transpose();
		setTransposeButtonName();
	}
	
	public void changeMode() {
		panel.getFrame().getEditor().changeMode();
	}

	public void setTransposeButtonName() {
		if(panel.getFrame().getEditor().isTransposed()) {
			transpose.setText("Transpose"); 
		} else {
			transpose.setText("Transpose Back");
		}
	}

	public void refreshRadioButtons(String type) {
		if (type != null) {
			if (type.equals(EMPTY)) empty.setSelected(true);
			if (type.equals(WATER)) water.setSelected(true);
			if (type.equals(LAND)) land.setSelected(true);	
			if (type.equals(RIVER)) river.setSelected(true);	
			if (type.equals(MOUNTAIN)) mountain.setSelected(true);	
			if (type.equals(TOWN)) town.setSelected(true);		
			if (type.equals(CITY)) city.setSelected(true);	
		}
	}





	// Getters & Setters
	public BPanel getPanel() {
		return panel;
	}

	public void setPanel(BPanel panel) {
		this.panel = panel;
	}

	public JButton getPrint() {
		return print;
	}

	public void setPrint(JButton print) {
		this.print = print;
	}

}
