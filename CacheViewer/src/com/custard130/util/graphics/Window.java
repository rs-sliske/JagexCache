package com.custard130.util.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.custard130.util.IO.WebIO;

public class Window extends Canvas {
	private static final long				serialVersionUID	= 1L;

	private JFrame							frame;

	private static HashMap<String, Window>	windows				= new HashMap<String, Window>();

	public static void init() {

	}

	public Window(String key, String title, int width, int height) {
		frame = new JFrame(title);
		setup(width, height);
		windows.put(key, this);
	}

	public static void addWindowWithImage(String key, String title,
			String address) {

		final BufferedImage image = WebIO.loadImageFromWeb(address);
		addWindowWithImage(key, title, image);

	}

	public static void addWindowWithImage(String key, String title,
			final BufferedImage image) {

		new Window(key, title, image.getWidth(), image.getHeight() + 30) {
			private static final long	serialVersionUID	= -4211049829020118604L;

			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, null);
				addTo(g);
			}
		};

	}
	
	

	private void setup(int width, int height) {
		frame.setPreferredSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.add(this);
	}

	public static Window get(String key) {
		return windows.get(key);
	}

	public static void repaintAll() {
		for (Window w : windows.values()) {
			w.repaint();
		}
	}

	public void addTo(Graphics g) {

	}

}
