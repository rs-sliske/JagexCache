package uk.sliske.viewer.graphics;

import java.util.ArrayList;

public class IndexedModel {
	private ArrayList<Vector4f>	positions;
	private ArrayList<Vector4f>	texCoords;
	private ArrayList<Vector4f>	normals;
	private ArrayList<Vector4f>	tangents;
	private ArrayList<Integer>	indices;
	private Vector4f			centre	= null;
	private Vector4f			size	= null;

	public IndexedModel() {
		positions = new ArrayList<Vector4f>();
		texCoords = new ArrayList<Vector4f>();
		normals = new ArrayList<Vector4f>();
		tangents = new ArrayList<Vector4f>();
		indices = new ArrayList<Integer>();
	}

	public void calcNormals() {
		for (int i = 0; i < indices.size(); i += 3) {
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);
			Vector4f v1 = positions.get(i1).sub(positions.get(i0));
			Vector4f v2 = positions.get(i2).sub(positions.get(i0));
			Vector4f normal = v1.cross(v2).normalized();
			normals.get(i0).set(normals.get(i0).add(normal));
			normals.get(i1).set(normals.get(i1).add(normal));
			normals.get(i2).set(normals.get(i2).add(normal));
		}
		for (int i = 0; i < normals.size(); i++)
			normals.get(i).set(normals.get(i).normalized());
	}

	public void calcTangents() {
		for (int i = 0; i < indices.size(); i += 3) {
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);
			Vector4f edge1 = positions.get(i1).sub(positions.get(i0));
			Vector4f edge2 = positions.get(i2).sub(positions.get(i0));
			float deltaU1 = texCoords.get(i1).getX() - texCoords.get(i0).getX();
			float deltaV1 = texCoords.get(i1).getY() - texCoords.get(i0).getY();
			float deltaU2 = texCoords.get(i2).getX() - texCoords.get(i0).getX();
			float deltaV2 = texCoords.get(i2).getY() - texCoords.get(i0).getY();
			float dividend = (deltaU1 * deltaV2 - deltaU2 * deltaV1);
			float f = dividend == 0 ? 0.0f : 1.0f / dividend;
			Vector4f tangent = new Vector4f(0, 0, 0);
			tangent.setX(f * (deltaV2 * edge1.getX() - deltaV1 * edge2.getX()));
			tangent.setY(f * (deltaV2 * edge1.getY() - deltaV1 * edge2.getY()));
			tangent.setZ(f * (deltaV2 * edge1.getZ() - deltaV1 * edge2.getZ()));
			tangents.get(i0).set(tangents.get(i0).add(tangent));
			tangents.get(i1).set(tangents.get(i1).add(tangent));
			tangents.get(i2).set(tangents.get(i2).add(tangent));
		}
		for (int i = 0; i < tangents.size(); i++)
			tangents.get(i).set(tangents.get(i).normalized());
	}

	private final void calcData() {
		final int entries = indices.size();

		float xMin = 0;
		float xMax = 0;
		float yMin = 0;
		float yMax = 0;
		float zMin = 0;
		float zMax = 0;

		Vector4f temp = new Vector4f(0, 0, 0);

		for (int i = 0; i < entries; i++) {
			final Vector4f v = positions.get(indices.get(i));
			temp = temp.add(v);
			if (v.getX() > xMax) xMax = v.getX();
			if (v.getX() < xMin) xMin = v.getX();

			if (v.getY() > yMax) yMax = v.getY();
			if (v.getY() < yMin) yMin = v.getY();

			if (v.getZ() > zMax) yMax = v.getZ();
			if (v.getZ() < zMin) yMin = v.getZ();
		}
		size = new Vector4f(xMax - xMin, yMax - yMin, zMax - zMin);
		centre = temp.div(entries);
	}

	public Vector4f getCentre() {
		if (centre == null) {
			calcData();
		}
		return centre;
	}

	public Vector4f getSize() {
		if (size == null) {
			calcData();
		}
		return size;
	}

	public ArrayList<Vector4f> getPositions() {
		return positions;
	}

	public ArrayList<Vector4f> getTexCoords() {
		return texCoords;
	}

	public ArrayList<Vector4f> getNormals() {
		return normals;
	}

	public ArrayList<Vector4f> getTangents() {
		return tangents;
	}

	public ArrayList<Integer> getIndices() {
		return indices;
	}
}