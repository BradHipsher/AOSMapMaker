package main.editRes;

import javax.swing.JOptionPane;

import main.gui.BFrame;

public final class EditorResources {

	// Upon editing, ensure everything is internally consistent

	// Check Consistency
	final public static int NUMBER_OF_MODES = 2;

	// Check Consistency
	final public static int MODE_ID_1X1H = 0;
	final public static int MODE_ID_1X2H = 1;
	// TODO add other modes
	//	final public static int MODE_ID_2X1H = 2;
	//	final public static int MODE_ID_2X2H = 3;
	//	final public static int MODE_ID_1X3H = 4;
	//	final public static int MODE_ID_3X1H = 5;
	//	final public static int MODE_ID_3X3H = 6;
	final public static int MODE_DEFAULT = MODE_ID_1X1H;

	// Check Consistency
	final public static int[][] PAGE_SCALES_W_H = {
			{1, 1},
			{1, 2}//,
			// TODO for other modes
	};

	// Check Consistency
	final public static int[][] W1W2H = {
			{7, 7, 6}, 
			{7, 7, 13}//,
			// TODO for other modes

	};




	final public static String getModeName(int mode) {
		return "L:" + PAGE_SCALES_W_H[mode][0] + " by S:" + PAGE_SCALES_W_H[mode][1];
	}



	public static int queryModeDialog(BFrame frame) {
		Object[] modeOptionNames = new String[NUMBER_OF_MODES];
		for (int i = 0; i < NUMBER_OF_MODES; i ++) {
			modeOptionNames[i] = getModeName(i);
		}
		String s = (String)JOptionPane.showInputDialog(
				frame,
				"Switch Modes? (resets tiles)",
				"Switch Modes",
				JOptionPane.PLAIN_MESSAGE,
				null,
				modeOptionNames,
				modeOptionNames[0]
				);
		int selected = -1;
		for (int i = 0; i < NUMBER_OF_MODES; i ++) {
			if (modeOptionNames[i].equals(s)) selected = i;
		}
		return selected;
	}


}
