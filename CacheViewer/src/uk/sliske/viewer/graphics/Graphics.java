package uk.sliske.viewer.graphics;

import java.io.File;

import com.custard130.util.IO.WebIO;

public class Graphics {

	public Graphics(File file) {
		int width = 800;
		int height = 600;
		
		Display display = new Display(width, height);
		RenderContext target = display.getFrameBuffer();
		Bitmap texture;
		
		Frame frame = new Frame(file.getName(),display);

		Mesh mesh = null;
		try {
			texture = new Bitmap(WebIO.loadBImageFromWeb("http://sliske.uk/img/captures/e8e289.png"));
			mesh = new Mesh(file);
		}  catch (Exception e) {
			e.printStackTrace();
			texture = new Bitmap(32, 32);
			for (int j = 0; j < texture.getHeight(); j++) {
				for (int i = 0; i < texture.getWidth(); i++) {
					texture.drawPixel(i, j, 
							(byte) (Math.random() * 255.0 + 0.5), 
							(byte) (Math.random() * 255.0 + 0.5), 
							(byte) (Math.random() * 255.0 + 0.5), 
							(byte) (Math.random() * 255.0 + 0.5));
				}
			}
		}
		
		Vertex minYVert = new Vertex(new Vector4f(-1, -1, 0, 1), 
										new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex midYVert = new Vertex(new Vector4f(0, 1, 0, 1), 
										new Vector4f(0.5f, 1.0f, 0.0f, 0.0f));
		Vertex maxYVert = new Vertex(new Vector4f(1, -1, 0, 1),
										new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		
		Matrix4f projection = new Matrix4f().initPerspective((float) Math.toRadians(70.0f), 
				(float) target.getWidth() / (float) target.getHeight(), 0.1f, 1000.0f);
		
		float rotCounter = 0.0f;
		long previousTime = System.nanoTime();
		
		while (frame.valid()) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime) / 1000000000.0);
			previousTime = currentTime;
			
			rotCounter += delta;
			
			final Vector4f size = mesh.getSize();
			float sizeL = size.length();
			if(sizeL==0)sizeL+=Float.MIN_NORMAL;
			final float scaleAmt = 1.0f/sizeL;
			
			Matrix4f translation = new Matrix4f().initTranslation(0.0f, 0.0f, 1.0f);
			Matrix4f rotation = new Matrix4f().initRotation(0.0f, rotCounter, 0.0f);
			Matrix4f scale = new Matrix4f().initScale(scaleAmt,scaleAmt, scaleAmt);
			Matrix4f transform = projection.mul(translation.mul(rotation.mul(scale)));
			
			target.clear((byte) 0x00);

			mesh.draw(target, transform, texture);
			display.swapBuffers();
			target.clearDepthBuffer(Float.MAX_VALUE);
		}
	}
}