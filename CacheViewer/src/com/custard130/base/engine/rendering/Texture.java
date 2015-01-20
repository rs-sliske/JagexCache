package com.custard130.base.engine.rendering;

import static com.custard130.base.engine.core.Util.createByteBuffer;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.custard130.base.engine.rendering.resourceManagement.TextureResource;

public class Texture {
	private static HashMap<String, TextureResource>	loadedTextures	= new HashMap<String, TextureResource>();
	
	private TextureResource							resource;
	private String									fileName;
	
	public Texture(String fileName) {
		this.fileName = fileName;
		TextureResource oldResource = loadedTextures.get(fileName);
		
		if (oldResource != null) {
			resource = oldResource;
		} else {
			resource = loadTexture(fileName);
			loadedTextures.put(fileName, resource);
		}
		resource.addReference();
	}
	
	// Texture(int width = 0, int height = 0, unsigned char* data = 0, GLenum
	// textureTarget = GL_TEXTURE_2D, GLfloat filter = GL_LINEAR, GLenum
	// attachment = GL_NONE);
	
	public Texture(int width, int height, ByteBuffer[] data, int textureTarget, float[] filter, int... attachments) {
		resource = new TextureResource(textureTarget, width, height, attachments.length, data ,
				filter , attachments);
		
		resource.addReference();
	}
	
	@Override
	protected void finalize() {
		if (resource.removeReference() && !fileName.isEmpty()) {
			loadedTextures.remove(fileName);
		}
	}
	
	public void bindAsRenderTarget() {
		resource.bindAsRenderTarget();
	}
	
	public void bind() {
		bind(0);
	}
	
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		resource.bind(0);
	}
	
	// public int getID() {
	// return resource.getID();
	// }
	
	private static TextureResource loadTexture(String fileName) {
		String[] splitArray = fileName.split("\\.");
		@SuppressWarnings("unused")
		String ext = splitArray[splitArray.length - 1];
		
		try {
			// TextureResource resource = new TextureResource(2);
			
			BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			
			ByteBuffer buffer = createByteBuffer(image.getHeight() * image.getWidth() * 4);
			
			boolean hasAlpha = image.getColorModel().hasAlpha();
			
			final int w = image.getWidth();
			
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * w + x];
					
					buffer.put((byte) (pixel >> 16 & 0xFF));
					buffer.put((byte) (pixel >> 8 & 0xFF));
					buffer.put((byte) (pixel & 0xFF));
					if (hasAlpha)
						buffer.put((byte) (pixel >> 24 & 0xFF));
					else
						buffer.put((byte) 0xFF);
				}
			}
			buffer.flip();
			
			return new TextureResource(GL_TEXTURE_2D, w, image.getHeight(), 1, new ByteBuffer[] { buffer },
					new float[] { GL_LINEAR }, GL_COLOR_ATTACHMENT0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}
