package code.hci;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;


	/**
	 * Initialises frame, 
	 * Declares frames properties which will stay the same throughout the programme.
	 * @param panel
	 */
	public Frame(JPanel panel) {
		setTitle("Questions");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(panel);
		setVisible(true);
		setResizable(false);
		pack();
	}

	/**
	 * Called to update the frames 
	 * @param panel
	 */
	public void updateFrame(JPanel panel) {
		removeAll();
		add(panel);
		repaint();
		pack();
	}


}
