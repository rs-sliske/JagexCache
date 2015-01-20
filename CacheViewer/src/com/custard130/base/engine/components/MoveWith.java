package com.custard130.base.engine.components;

import com.custard130.base.engine.core.Entity;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;

public class MoveWith extends GameComponent {
	Entity	target;
	
	public MoveWith(Entity target) {
		this.target = target;
	}
	
	public void update(float delta) {
		getTransform().setPos(target.getTransform().getTransformedPos());
	}

	@Override
	public void input(float delta) {
		
	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
	}
}
