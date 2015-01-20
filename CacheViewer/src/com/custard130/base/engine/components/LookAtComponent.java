package com.custard130.base.engine.components;

import com.custard130.base.engine.core.Quaternion;
import com.custard130.base.engine.core.Entity;
import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;

public class LookAtComponent extends GameComponent {
	
	@SuppressWarnings("unused")
	private RenderingEngine	renderingEngine;
	
	@SuppressWarnings("unused")
	private boolean			useMainCamera	= false;
	
	private Entity		target;
	
	public LookAtComponent() {
		useMainCamera = true;
	}
	
	public LookAtComponent(Entity target) {
		this.target = target;
	}
	
	@Override
	public void update(float delta) {
//		if (useMainCamera) {
//			if (renderingEngine != null) {
//				Quaternion newRot = getTransform().getLookAtDirection(
//						renderingEngine.getMainCamera().getTransform().getPos(), new Vector3f(0, 1, 0));
//				getTransform().setRot(getTransform().getRot().slerp(newRot, delta * 5.0f, true));
//			}
//			return;
//		}
		if (target != null) {
			Quaternion newRot = getTransform().getLookAtDirection(
					target.getTransform().getTransformedPos().mul(-1f,1f,1f), new Vector3f(0, 1, 0)).mul(-1.0f);
			getTransform().setRot(getTransform().getRot().slerp(newRot, delta * 10.0f, true));
		}
	}

	@Override
	public void input(float delta) {
		
	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
	}
}
