package com.custard130.base.engine.core;

import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;

public interface CoreFuncions {
	public void input(float delta);
	
	public void update(float delta);
	
	public void render(Shader shader, RenderingEngine renderingEngine);
	
}
