package com.custard130.base.engine.core;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import com.custard130.base.engine.rendering.Vertex;
import com.custard130.base.engine.rendering.meshloading.IndexedModel;

public class Util {
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}
	
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}
	
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
		
		for (int i = 0; i < vertices.length; i++) {
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
			buffer.put(vertices[i].getTexCoord().getX());
			buffer.put(vertices[i].getTexCoord().getY());
			buffer.put(vertices[i].getNormal().getX());
			buffer.put(vertices[i].getNormal().getY());
			buffer.put(vertices[i].getNormal().getZ());
			buffer.put(vertices[i].getTangent().getX());
			buffer.put(vertices[i].getTangent().getY());
			buffer.put(vertices[i].getTangent().getZ());
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4f value) {
		FloatBuffer buffer = createFloatBuffer(4 * 4);
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				buffer.put(value.get(i, j));
		
		buffer.flip();
		
		return buffer;
	}
	
	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < data.length; i++)
			if (!data[i].equals("")) result.add(data[i]);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];
		
		for (int i = 0; i < data.length; i++)
			result[i] = data[i].intValue();
		
		return result;
	}
	
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}
	public static boolean saveIndexedModel(final IndexedModel model, final String fileName) {
		final boolean success = false;
		new Thread("Exporting model to "+fileName){
			public void run(){
				saveIndexedModelToFile(model,fileName);
			}
		}.start();
		
		return success;
		
	}
	private static boolean saveIndexedModelToFile(IndexedModel model, String fileName) {
		try {
			@SuppressWarnings("resource")
			FileWriter file = new FileWriter(fileName);
			file.append("# indexed model generated on \n");
			for (Vector3f pos : model.getPositions()) {
				file.append("v");
				file.append(" " + pos.getX());
				file.append(" " + pos.getY());
				file.append(" " + pos.getZ());
				file.append("\n");
			}
			for (Vector2f tex : model.getTexCoords()) {
				file.append("vt");
				file.append(" " + tex.getX());
				file.append(" " + tex.getY());
				file.append("\n");
			}
			for (Vector3f normal : model.getNormals()) {
				file.append("vn");
				file.append(" " + normal.getX());
				file.append(" " + normal.getY());
				file.append(" " + normal.getZ());
				file.append("\n");
			}
			for (int i = 0; i < model.getIndices().size(); i ++) {
				file.append("f");
				//for (int j = 0; j < 9; j++)
					file.append(" " + model.getIndices().get(i));
				file.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
