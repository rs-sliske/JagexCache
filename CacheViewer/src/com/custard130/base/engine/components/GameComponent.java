package com.custard130.base.engine.components;

import com.custard130.base.engine.core.CoreEngine;
import com.custard130.base.engine.core.CoreFuncions;
import com.custard130.base.engine.core.GameObject;
import com.custard130.base.engine.core.Transform;
import com.custard130.base.engine.core.Entity;

public abstract class GameComponent extends Entity implements CoreFuncions{
	private GameObject parent;

	
	public final void setParent(GameObject parent) {
		this.parent = parent;
	}

	public final Transform getTransform() {
		return getParent().getTransform();
	}

	public void addToEngine(CoreEngine engine) {
		
	}

	public final GameObject getParent() {
		return parent;
	}
}
