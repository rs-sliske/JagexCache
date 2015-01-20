package com.custard130.base.engine.core;

public class Entity {
	private Transform					transform;
	protected Entity(){
		transform = new Transform();
	}
	public Transform getTransform(){
		return transform;
	}
	
}
