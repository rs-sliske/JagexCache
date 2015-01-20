package com.custard130.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import java.nio.ByteBuffer;

public class TextureResource {
	
	private int		refCount;
	
	private int[]	id;
	
	private int		numTextures;
	private int		width;
	private int		height;
	private int		textureTarget;
	private int		frameBuffer;
	private int		renderBuffer;
	
	public TextureResource(int textureTarget, int width, int height, int numTextures, ByteBuffer[] data,
			float[] filters, int... attachments) {
		this.numTextures = numTextures;
		this.textureTarget = textureTarget;
		id = new int[numTextures];
		this.width = width;
		this.height = height;
		frameBuffer = 0;
		renderBuffer = 0;
		initTextures(data, filters);
		
		initRenderTargets(attachments);
	}
	
	@Override
	protected void finalize() {
		for (int i : id)
			glDeleteBuffers(i);
		if (frameBuffer != 0) {
			glDeleteBuffers(frameBuffer);
		}
		if (renderBuffer != 0) {
			glDeleteBuffers(renderBuffer);
		}
	}
	
	public void addReference() {
		refCount++;
	}
	
	public boolean removeReference() {
		refCount--;
		return !(refCount > 0);
	}
	
	public void bind(int textureNum) {
		glBindTexture(GL_TEXTURE_2D, id[textureNum]);
	}
	
	private void initTextures(ByteBuffer[] data, float filter[]) {
		for (int i = 0; i < numTextures; i++) {
			id[i] = glGenTextures();
			glBindTexture(textureTarget, id[i]);
			
		//	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
		//	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);
			
			glTexParameterf(textureTarget, GL_TEXTURE_MIN_FILTER, filter[i]);
			glTexParameterf(textureTarget, GL_TEXTURE_MAG_FILTER, filter[i]);
			
			glTexImage2D(textureTarget, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data[i]);
		}
	}
	
	private void initRenderTargets(int... attachments) {
		if (attachments.length == 0) return;
		
		int[] drawBuffers = new int[numTextures];
		
		boolean hasDepth = false;
		
		for (int i = 0; i < numTextures; i++) {
			if (attachments[i] == GL_NONE) continue;
			
			if (attachments[i] == GL_DEPTH_ATTACHMENT) {
				drawBuffers[i] = GL_NONE;
				hasDepth = true;
			} else {
				drawBuffers[i] = attachments[i];
			}
			
			if (frameBuffer == 0) {
				frameBuffer = glGenFramebuffers();
				glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBuffer);
			}
			glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, attachments[i], textureTarget, id[i], 0);
			
		}
		if (frameBuffer == 0) return;
		
		if(!hasDepth){
			renderBuffer = glGenRenderbuffers();
			glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
			glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_RENDERBUFFER, frameBuffer, renderBuffer);
		}
		for (int i = 0; i < numTextures; i++) {
			glDrawBuffers(drawBuffers[i]);
		}
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Framebuffer creation failed");
			assert false;
		}
		
	}
	
	public void bindAsRenderTarget() {
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBuffer);
		glViewport(0, 0, width, height);
	}
	
}
