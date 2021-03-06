package uk.sliske.viewer.graphics;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Frame {
	private final JFrame	frame;

	public Frame(String title, Canvas canvas) {
		frame = new JFrame();
		frame.add(canvas);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setTitle(title);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.requestFocus();
	}
	
	public boolean valid(){
		return frame.isVisible();
	}
	
}
