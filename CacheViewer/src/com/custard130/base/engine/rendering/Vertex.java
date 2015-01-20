package com.custard130.base.engine.rendering;

import com.custard130.base.engine.core.Vector2f;
import com.custard130.base.engine.core.Vector3f;

public class Vertex {
	public static final int	SIZE	= 11;
	
	private Vector3f		pos;
	private Vector2f		texCoord;
	private Vector3f		normal;
	private Vector3f		tangent;
	
	public Vertex(Vector3f pos) {
		this(pos, new Vector2f(0, 0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord) {
		this(pos, texCoord, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal) {
		this(pos, texCoord, normal, new Vector3f(0, 0, 0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal, Vector3f tangent) {
		this.pos = (Vector3f) pos;
		this.texCoord = texCoord;
		this.normal = (Vector3f) normal;
		this.setTangent(tangent);
	}
	
	public Vector3f getPos() {
		return pos;
	}
	
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
	
	public Vector2f getTexCoord() {
		return texCoord;
	}
	
	public void setTexCoord(Vector2f texCoord) {
		this.texCoord = texCoord;
	}
	
	public Vector3f getNormal() {
		return normal;
	}
	
	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
	
	public Vector3f getTangent() {
		return tangent;
	}
	
	public void setTangent(Vector3f tangent) {
		this.tangent =  tangent;
	}
}
