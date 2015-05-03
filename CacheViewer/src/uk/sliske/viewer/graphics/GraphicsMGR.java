package uk.sliske.viewer.graphics;

import java.io.File;
import java.io.IOException;

import uk.sliske.viewer.background.Util;

import com.custard130.util.IO.WebIO;

public class GraphicsMGR {
	private static GraphicsMGR	currentGraphics;
	private boolean				running			= true;
	private Display				display;	

	public static GraphicsMGR get() {
		return currentGraphics;
	}	

	public static GraphicsMGR showModel_obj(final int id) throws IOException {
		File f = Util.modelfilemgr_obj(id);
		if (f == null) return null;
		return new GraphicsMGR(f);

	}

	public static GraphicsMGR showModel_obj(final int id, Display d) throws IOException {
		File f = Util.modelfilemgr_obj(id);
		if (f == null) return null;
		return new GraphicsMGR(f, d);
	}

	public static GraphicsMGR showModel_npc(final int id) throws IOException {
		File f = Util.modelfilemgr_npc(id);
		if (f == null) return null;
		return new GraphicsMGR(f);

	}

	public static GraphicsMGR showModel_npc(final int id, Display d) throws IOException {
		File f = Util.modelfilemgr_npc(id);
		if (f == null) return null;
		return new GraphicsMGR(f, d);
	}

	public GraphicsMGR(File file) throws IOException {
		this(file, new Display(800, 600));
	}

	public Display getCanvas() {
		return display;
	}

	public GraphicsMGR(File file, final Display display) throws IOException {
		if (currentGraphics != null) {
			currentGraphics.running = false;
		}
		currentGraphics = this;

		final RenderContext target = display.getFrameBuffer();
		final Bitmap texture = new Bitmap(
				WebIO.loadBImageFromWeb("http://www.trikkiworld.com/images/bg/bg_sand/25012011/sand006.jpeg"));

		final Mesh mesh = new Mesh(file);

		final Matrix4f projection = new Matrix4f()
				.initPerspective((float) Math.toRadians(70.0f), (float) target.getWidth() / (float) target
						.getHeight(), 0.1f, 1000.0f);

		new Thread() {
			public void run() {
				float rotCounter = 0.0f;
				long previousTime = System.nanoTime();
				while (running) {
					long currentTime = System.nanoTime();
					float delta = (float) ((currentTime - previousTime) / 1000000000.0);
					previousTime = currentTime;

					rotCounter += delta;

					final Vector4f size = mesh.getSize();
					float sizeL = size.length();
					if (sizeL == 0) sizeL += Float.MIN_NORMAL;
					final float scaleAmt = 1.0f / sizeL;

					Matrix4f translation = new Matrix4f().initTranslation(0.0f, 0.0f, 1.0f);
					Matrix4f rotation = new Matrix4f().initRotation(0.0f, rotCounter, 0.0f);
					Matrix4f scale = new Matrix4f().initScale(scaleAmt, scaleAmt, scaleAmt);
					Matrix4f transform = projection.mul(translation.mul(rotation.mul(scale)));

					target.clear((byte) 0x00);

					mesh.draw(target, transform, texture);
					display.swapBuffers();
					target.clearDepthBuffer(Float.MAX_VALUE);
				}
			}
		}.start();
	}
}