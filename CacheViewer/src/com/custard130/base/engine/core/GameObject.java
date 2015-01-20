package com.custard130.base.engine.core;

import java.util.ArrayList;

import com.custard130.base.engine.components.GameComponent;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;

public class GameObject extends Entity implements CoreFuncions{
	private ArrayList<GameObject>		children;
	private ArrayList<GameComponent>	components;
	
	private CoreEngine					engine;
	private GameObject					parent;
	
	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		
		engine = null;
	}
	
	public GameObject(GameObject parent) {
		this();
		parent.addChild(this);
		this.parent = parent;
	}
	
	public final GameObject addChild(GameObject child) {
		children.add(child);
		child.getTransform().setParent(getTransform());
		child.setEngine(engine);
		child.parent = this;
		return this;
	}
	
	public final GameObject addComponent(GameComponent component) {
		components.add(component);
		component.setParent(this);
		return this;
	}
	
	public final void inputAll(float delta) {
		input(delta);
		
		for (GameObject child : children)
			child.inputAll(delta);
	}
	
	public final void updateAll(float delta) {
		update(delta);
		
		for (GameObject child : children)
			child.updateAll(delta);
	}
	
	public final void renderAll(Shader shader, RenderingEngine renderingEngine) {
		render(shader, renderingEngine);
		
		for (GameObject child : children)
			child.renderAll(shader, renderingEngine);
	}
	
	public final ArrayList<GameObject> getAllAttached() {
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		for (GameObject child : children) {
			result.addAll(child.getAllAttached());
		}
		result.add(this);
		return result;
	}
	
	public final GameObject getParent() {
		return parent;
	}
	
	public final void setEngine(CoreEngine engine) {
		if (this.engine != engine) {
			this.engine = engine;
			
			for (GameComponent component : components)
				component.addToEngine(engine);
			
			for (GameObject child : children)
				child.setEngine(engine);
		}
	}
	
	public final void input(float delta) {		
		for (GameComponent component : components)
			component.input(delta);
	}
	
	public final void update(float delta) {
		getTransform().update();
		updateThis();
		for (GameComponent component : components)
			component.update(delta);
	}
	
	public final void render(Shader shader, RenderingEngine renderingEngine) {
		for (GameComponent component : components)
			component.render(shader, renderingEngine);
	}
	
	protected void updateThis(){
		
	}
}
