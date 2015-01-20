package com.custard130.base.engine.core;

public class Transform {
	private Vector3f	pos;
	private Quaternion	rot;
	private Vector3f	scale;
	private Transform	parent;
	private Matrix4f	parentMatrix;
	
	private Vector3f	oldPos;
	private Quaternion	oldRot;
	private Vector3f	oldScale;
	
	private Vector3f	velocity;
	
	public Transform() {
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(0, 0, 0, 1);
		scale = new Vector3f(1, 1, 1);
		parentMatrix = new Matrix4f().initIdentity();
		velocity = new Vector3f(0);
	}
	
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}
	
	private Matrix4f getParentMatrix() {
		if ((parent != null) && hasChanged()) parentMatrix = parent.getTransformation();
		return parentMatrix;
	}
	
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}
	
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 1, 1, 1);
		if (parent != null) {
			parentRotation = parent.getTransformedRot();
		}
		return parentRotation.mul(rot);
	}
	
	public void update() {
		if (oldPos == null) {
			oldPos = new Vector3f(0);
			oldRot = new Quaternion();
			oldScale = new Vector3f(1);
			velocity = new Vector3f(0);
		} else {
			oldPos.set(pos);
			oldRot.set(rot);
			oldScale.set(scale);
			velocity = (Vector3f) pos.sub(oldPos);
		}
	}
	
	public boolean hasChanged() {
		if (oldPos == null) return true;
		if (parent != null && parent.hasChanged()) return true;
		if (!oldPos.equals(getPos())) return true;
		if (!oldRot.equals(getRot())) return true;
		if (!oldScale.equals(getScale())) return true;
		return false;
	}
	
	public Transform setParent(Transform parent) {
		this.parent = parent;
		return this;
	}
	
	public Vector3f getPos() {
		return pos;
	}
	
	public Transform setPos(Vector3f pos) {
		this.pos = (Vector3f) pos;
		return this;
	}
	
	public Quaternion getRot() {
		return rot;
	}
	
	public Transform setRot(Quaternion rotation) {
		this.rot = rotation;
		return this;
	}
	
	public Transform rotate(Vector3f axis, float amount) {
		setRot(getRot().mul(new Quaternion(axis, amount)));// .normalized());
		return this;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public Transform setScale(Vector3f scale) {
		this.scale = (Vector3f) scale;
		return this;
	}
	
	public Transform scale(float x, float y, float z) {
		return scale(new Vector3f(x, y, z));
	}
	
	public Transform scale(Vector3f amt) {
		return setScale(getScale().mul(amt));
	}
	
	public Transform move(Vector3f amt) {
		setPos(getPos().add(amt));
		return this;
	}
	
	public Transform move(float dx, float dy, float dz) {
		return move(new Vector3f(dx, dy, dz));
		
	}
	
	public void lookAt(Vector3f point, Vector3f up) {
		setRot(getLookAtDirection(point, up));
	}
	
	public Quaternion getLookAtDirection(Vector3f vector3, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(vector3.sub(pos).normalized(), up));
	}
	
	public Vector3f getVelocity() {
		return velocity;
	}
	
}
