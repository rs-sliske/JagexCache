package com.custard130.base.engine;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.custard130.base.engine.core.Time;

public class Debug {

	private static boolean debug = true;
	private static JFrame debugFrame;
	private static JTextArea text = new JTextArea();
	
	static{
		initFrame();
		enabled(true);
	}

	public static void println(final Object s) {
		if (debug) {
			System.out.println(s);
		}
		text.append(Time.getCurrentTime() + " - " + s + "\n\r");

	}

	public static void printf(final String s, Object... objects) {
		println(String.format(s, objects));
	}

	public static void enabled(final boolean b) {
		debug = b;
		debugFrame.setVisible(b);
	}

	protected final static void toggle() {
		enabled(!debug);
	}

	protected static void initFrame() {
		debugFrame = new JFrame("Sliske Debugging");
		debugFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Dimension size = new Dimension(500, 800);
		debugFrame.setPreferredSize(size);
		debugFrame.setSize(size);
		debugFrame.setResizable(true);
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(text);
		debugFrame.getContentPane().add(pane, BorderLayout.CENTER);
		// debugFrame.getContentPane().add(bar, BorderLayout.EAST);
		text.setEditable(false);

	}

}
