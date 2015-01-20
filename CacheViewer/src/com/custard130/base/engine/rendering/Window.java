package com.custard130.base.engine.rendering;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL12.*;
//import static org.lwjgl.opengl.GL13.*;
//import static org.lwjgl.opengl.GL14.*;
//import static org.lwjgl.opengl.GL15.*;
//import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.custard130.base.engine.Debug;
import com.custard130.base.engine.core.Vector2f;

public class Window {
	public static void createWindow(int width, int height, String title) {

		Display.setTitle(title);
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void changeCanvas(final Canvas newCanvas){
		try {
			Display.setParent(newCanvas);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void bindAsRenderTarget(){
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER,0);
		glViewport(0,0,getWidth(),getHeight());
	}

	public static void render() {
		Display.update();
	}

	public static void dispose() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}

	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	public static int getWidth() {
		return Display.getDisplayMode().getWidth();
	}

	public static int getHeight() {
		return Display.getDisplayMode().getHeight();
	}

	public static String getTitle() {
		return Display.getTitle();
	}

	public static Vector2f getCenter() {
		return new Vector2f(getWidth() / 2, getHeight() / 2);
	}

	public static boolean makeFullscreen(final boolean fullscreen_enabled) {
		if (fullscreen_enabled) {
			DisplayMode oldDisplayMode = Display.getDisplayMode();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			DisplayMode mode = new DisplayMode(d.width, d.height);
			Debug.println("new mode can be fullscreen "
					+ mode.isFullscreenCapable());
			try {
				DisplayMode displayMode = null;
				DisplayMode[] modes = Display.getAvailableDisplayModes();

				for (DisplayMode dm : modes) {
					if (dm.isFullscreenCapable()) {
						displayMode = dm;
						// break;
					}
				}
				if (displayMode != null) {
					Display.setDisplayMode(displayMode);
				}
				// Display.
				Display.setFullscreen(fullscreen_enabled);
			} catch (LWJGLException e) {
				try {
					Display.setDisplayMode(oldDisplayMode);
				} catch (LWJGLException e1) {
					System.exit(1);
				}
				Debug.println("failed to make display fullscreen");
				return false;
			}
		}
		return Display.isFullscreen();

	}

}
