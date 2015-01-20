package com.custard130.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.custard130.base.engine.core.Util;
import com.custard130.base.engine.core.Vector2f;
import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.rendering.meshloading.IndexedModel;
import com.custard130.base.engine.rendering.meshloading.OBJModel;
import com.custard130.base.engine.rendering.resourceManagement.MeshResource;

public class Mesh {

	private static HashMap<String, MeshResource> loadedModels = new HashMap<String, MeshResource>();

	private MeshResource resource;
	private String fileName;

	protected Vector3f centre = new Vector3f(0);

	public Mesh(String fileName) {
		this.fileName = fileName;
		MeshResource oldResource = loadedModels.get(fileName);

		if (oldResource != null) {
			resource = oldResource;
		} else {
			resource = new MeshResource();
			loadMesh(fileName);
			loadedModels.put(fileName, resource);
		}
		resource.addReference();
	}

	public Mesh(Vertex[] vertices, int[] indices) {
		this(vertices, indices, false);
	}

	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
		resource = new MeshResource();
		fileName = "";
		addVertices(vertices, indices, calcNormals);
	}

	@Override
	protected void finalize() {
		if (resource.removeReference() && !fileName.isEmpty()) {
			loadedModels.remove(fileName);
		}
	}

	protected void addVertices(Vertex[] vertices, int[] indices,
			boolean calcNormals) {
		resource.setSize(indices.length);

		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices),
				GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,
				Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}

	public void draw() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);

		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());

		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
	}

	private Mesh loadMesh(String fileName) {
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		if (!ext.equals("obj")) {
			System.err
					.println("Error: File format not supported for mesh data: "
							+ ext);
			new Exception().printStackTrace();
			System.exit(1);
		}
		OBJModel test = new OBJModel("./res/models/" + fileName);
		IndexedModel model = test.toIndexedModel();

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for (int i = 0; i < model.getPositions().size(); i++) {
			vertices.add(new Vertex(model.getPositions().get(i), model
					.getTexCoords().get(i), model.getNormals().get(i), model
					.getTangents().get(i)));
		}
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);
		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);
		addVertices(vertexData, Util.toIntArray(indexData), false);
		centre = centre(vertexData);
		return null;
	}

	protected static Vector3f centre(Vertex[] vertices) {
		Vector3f res = new Vector3f(0);
		float amt = vertices.length;

		for (Vertex v : vertices) {
			res = res.add(v.getPos());
		}

		return res.div(amt);
	}

	public Vector3f getCentre() {
		return centre;
	}

	public static Mesh plane(float size) {
		return plane(size, size);
	}

	public static Mesh plane(float width, float depth) {
		return plane(width, 0, depth);

	}

	public static Mesh plane(float width, float height, float depth) {
		Vertex[] vertices = new Vertex[] {
				new Vertex(new Vector3f(-width, -height, -depth), new Vector2f(
						0.0f, 0.0f)),
				new Vertex(new Vector3f(-width, height, depth), new Vector2f(
						0.0f, 1.0f)),
				new Vertex(new Vector3f(width, -height, -depth), new Vector2f(
						1.0f, 0.0f)),
				new Vertex(new Vector3f(width, height, depth), new Vector2f(
						1.0f, 1.0f)) };

		int indices[] = { 0, 1, 2, 2, 1, 3, 3, 1, 2, 2, 1, 0 };

		return new Mesh(vertices, indices, true);
	}

	public static Mesh box(Vector3f min, Vector3f max) {
		float x1 = min.getX();
		float y1 = min.getY();
		float z1 = min.getZ();
		float x2 = max.getX();
		float y2 = max.getY();
		float z2 = max.getZ();

		Vertex[] vertices = new Vertex[] {
				new Vertex(new Vector3f(x1, y1, z1), new Vector2f(0.0f, 0.0f)), // 0
				new Vertex(new Vector3f(x1, y2, z1), new Vector2f(0.0f, 1.0f)), // 1
				new Vertex(new Vector3f(x2, y1, z1), new Vector2f(1.0f, 0.0f)), // 2
				new Vertex(new Vector3f(x2, y2, z1), new Vector2f(1.0f, 1.0f)), // 3
				new Vertex(new Vector3f(x1, y1, z2), new Vector2f(1.0f, 1.0f)), // 4
				new Vertex(new Vector3f(x1, y2, z2), new Vector2f(1.0f, 0.0f)), // 5
				new Vertex(new Vector3f(x2, y1, z2), new Vector2f(0.0f, 0.0f)), // 6
				new Vertex(new Vector3f(x2, y2, z2), new Vector2f(0.0f, 1.0f)) }; // 7

		int indices[] = { 0, 1, 2, 2, 1, 3, 2, 3, 6, 6, 3, 7, 6, 7, 4, 4, 7, 5,
				4, 5, 0, 0, 5, 1, 1, 5, 3, 3, 5, 7, 0, 2, 4, 4, 2, 6 };

		return new Mesh(vertices, indices, true);
	}

	public static Mesh box(Vector3f size) {
		Vector3f halfSize = size.div(2.0f);
		return box(halfSize.mul(-1), halfSize);
	}

	public static Mesh box(float width, float hieght, float depth) {
		return box(new Vector3f(width, hieght, depth));
	}

	public static Mesh cube(float size) {
		return box(size, size, size);
	}

	public static Mesh customMesh(Vector3f... vertices) {
		Vertex[]verts = new Vertex[1];
		int[] indices = new int[1];
		return new Mesh(verts, indices, true);
	}
}
