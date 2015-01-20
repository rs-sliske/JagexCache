package com.custard130.util.graphics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Image extends BufferedImage {

	private int[]							pixels;
	private Point							firstFound;

	private static HashMap<Integer, Image>	levelImages	= new HashMap<Integer, Image>();

	public Image(BufferedImage img) {
		super(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.getGraphics().drawImage(img, 0, 0, null);
		pixels = new int[getWidth() * getHeight()];
		getRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
	}

	public Image(BufferedImage img, int x, int y, int width, int height) {
		this(img.getSubimage(x, y, width, height));
	}

	public static Image getImageFromLevel(BufferedImage img, int lvl) {
		if (levelImages.containsKey(lvl)) {
			return levelImages.get(lvl);
		}
		int col = lvl % 10;
		int row = lvl / 10;
		int width = 12;
		int height = 8;
		int x = col * width;
		int y = row * height;
		Image result = new Image(img, x, y, width, height);
		levelImages.put(lvl, result);
		return result;
	}

	public int getPixelAt(int x, int y) {
		return pixels[x + y * getWidth()];
	}

	public Point[] findIn(Image image) {
		int colorOfFirstPixel = getPixelAt(0, 0);
		ArrayList<Point> points = new ArrayList<Point>();
		int i = 0;
		if (image != null) {
			for (int x0 = 0; x0 < image.getWidth() - getWidth(); x0++) {
				for (int y0 = 0; y0 < image.getHeight() - getHeight(); y0++) {
					if (image.getPixelAt(x0, y0) != colorOfFirstPixel) {
						continue;
					}
					i = 0;
					for (int x1 = 0; x1 < getWidth(); x1++) {
						for (int y1 = 0; y1 < getHeight(); y1++) {
							if (image.getPixelAt(x0 + x1, y0 + y1) == getPixelAt(
									x1, y1)) {
								i++;
							}

						}
					}
					if (i == pixels.length) {
						Point p = new Point(x0, y0);
						points.add(p);
						if (firstFound == null) {
							firstFound = p;
						}
					}
				}
			}
		}
		Point[] res = new Point[points.size()];
		points.toArray(res);

		return res;
	}

	public Rectangle generateRec(int width, int height) {
		Dimension d = new Dimension(width, height);
		return generateRec(d);

	}

	public Rectangle generateRec(Dimension d) {
		if (firstFound == null) {
			return null;
		}
		return new Rectangle(firstFound, d);

	}

	public int[] getPixels() {
		return pixels;
	}

}
