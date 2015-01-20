package com.custard130.base.engine.rendering.resourceManagement;

import java.util.HashMap;

import com.custard130.base.engine.core.Vector3f;

public abstract class MappedValues {
	private HashMap<String, Vector3f>	vectors;
	private HashMap<String, Float>		floats;
	
	protected MappedValues(){
		vectors = new HashMap<String, Vector3f>();
		floats = new HashMap<String, Float>();
	}
	
	public void addVector(String name, Vector3f vector) {
		vectors.put(name, vector);
	}
	
	public Vector3f getVector(String name) {
		Vector3f res = vectors.get(name);
		if (res != null) return res;
		res = new Vector3f(0);
		addVector(name, res);
		return res;
	}
	
	public void addFloat(String name, float float0) {
		floats.put(name, float0);
	}
	
	public float getFloat(String name) {
		Float res = floats.get(name);
		if (res != null) return res;
		float r = 0;
		addFloat(name, r);
		return r;
	}
}
