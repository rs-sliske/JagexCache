package uk.sliske.viewer.graphics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mesh {

	private List<Vertex>	vertices;
	private List<Integer>	indices;
	private Vector4f size;

	public Mesh(String fileName) throws IOException {
		this(new File(fileName));
	}
	
	public Mesh(File file) throws IOException {
		IndexedModel model = new OBJModel(file).toIndexedModel();

		vertices = new ArrayList<Vertex>();
		
		Vector4f centre = new Vector4f(0, 0, 0);
		centre = model.getCentre();
		centre.setW(0);
		
		size = model.getSize();
		
		//System.out.println(centre);
		
		for (int i = 0; i < model.getPositions().size(); i++) {
			vertices.add(new Vertex(model.getPositions().get(i).sub(centre), model.getTexCoords().get(i)));
		}
		indices = model.getIndices();
	}

	public void draw(RenderContext context, Matrix4f transform, Bitmap texture) {
		for (int i = 0; i < getNumIndices(); i += 3) {
			context.drawTriangle(getVertex(getIndex(i)).transform(transform), getVertex(getIndex(i + 1))
					.transform(transform), getVertex(getIndex(i + 2)).transform(transform), texture);
		}
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public int getNumIndices() {
		return indices.size();
	}

	public int getIndex(int index) {
		return indices.get(index);
	}

	public Vertex getVertex(int index) {
		return vertices.get(index);
	}
	public Vector4f getSize(){
		if(size==null)return Vector4f.NULL;
		return size;
	}
}
