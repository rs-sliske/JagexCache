package com.custard130.util.IO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class WebIO {

	// private static final BufferedImage NULL_IMAGE = new BufferedImage(1,1,0);

	public static BufferedImage loadBImageFromWeb(URL url) {
		InputStream stream;
		// System.out.println(url.toString());
		try {
			stream = url.openStream();
			final BufferedImage image = ImageIO.read(stream);
			return image;
		} catch (IOException e) {
			//new SliskeError("failed to load image from url",e.getStackTrace());
		}
		return null;
	}

	public static BufferedImage loadBImageFromWeb(String address) {
		try {
			return loadBImageFromWeb(new URL(address));
		} catch (MalformedURLException e) {
			//new SliskeError("failed to load image from string",e.getStackTrace());
		}
		return null;
	}
}
