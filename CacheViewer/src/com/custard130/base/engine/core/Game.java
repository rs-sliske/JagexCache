package com.custard130.base.engine.core;

import com.custard130.base.engine.components.GameComponent;
import com.custard130.base.engine.rendering.RenderingEngine;

public abstract class Game {
	private GameObject	root;
	
	public void init() {
	}
	
	public void input(float delta) {
		getRootObject().inputAll(delta);
	}
	
	public void update(float delta) {
		getRootObject().updateAll(delta);
	}
	
	public void render(RenderingEngine renderingEngine) {
		renderingEngine.render(getRootObject());
	}
	
	private GameObject getRootObject() {
		if (root == null) root = new GameObject();
		
		return root;
	}
	
	public void setEngine(CoreEngine engine){
		getRootObject().setEngine(engine);
	}
	
	public void addToRoot(GameComponent component) {
		getRootObject().addComponent(component);
	}
	
	public void addToRoot(GameObject child) {
		getRootObject().addChild(child);
	}
	
	public float toRadians(float angle) {
		return (float) Math.toRadians(angle);
	}
}
