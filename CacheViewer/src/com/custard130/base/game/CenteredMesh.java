package com.custard130.base.game;

import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.rendering.Mesh;
import com.custard130.base.engine.rendering.Vertex;

public class CenteredMesh extends Mesh {
	
	public CenteredMesh(String fileName) {
		super(fileName);
	}
	
	@Override
	protected void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
		int length = vertices.length;
		centre = centre(vertices);		
		Vertex[] newVertices = new Vertex[length];
		Vector3f tempCentre = new Vector3f(0);
		for (Vertex v : vertices) {
			tempCentre = tempCentre.add(v.getPos());
		}		
		tempCentre = new Vector3f(0);
		for (int i = 0; i < length; i++) {			
			Vertex oldv = vertices[i];
			Vertex newv = new Vertex((oldv.getPos().sub(centre)), oldv.getTexCoord(), oldv.getNormal(),
					oldv.getTangent());
			newVertices[i] = newv;
		}
		tempCentre = new Vector3f(0);
		for (Vertex v : newVertices) {
			tempCentre = tempCentre.add(v.getPos());
		}		
		super.addVertices(newVertices, indices, calcNormals);
	}
	
}
