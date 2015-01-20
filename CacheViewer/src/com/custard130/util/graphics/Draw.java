package com.custard130.util.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Draw {

	public static void listOfString(Graphics g, ArrayList<String> args, int x,
			int y) {
		int line = 0;
		g.setColor(Color.BLACK);
		for (String s : args) {
			g.drawString(s, x, getY(y,line));
			line++;
		}
	}

	private static int getY(int y, int line) {
		return (line * 15) + y;
	}

}
