package com.custard130.base.engine.components;

import com.custard130.base.engine.core.Vector2f;
import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.core.input.Input;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;
import com.custard130.base.engine.rendering.Window;

public class MouseRot extends GameComponent {
	public static final Vector3f	yAxis		= new Vector3f(0, 1, 0);
	
	private static final float		SPEED		= 0.5f;
	private static final int		ESC_KEY		= Input.KEY_ESCAPE;
	
	private float					speed;
	private int						escKey;
	
	boolean							mouseLocked	= false;
	
	public MouseRot(){
		this(SPEED,ESC_KEY);
	}
	public MouseRot(int escKey) {
		this(SPEED, escKey);
	}
	
	public MouseRot(float speed) {
		this(speed, ESC_KEY);
	}
	
	public MouseRot(float speed, int escKey) {
		super();
		this.escKey = escKey;
		this.speed = speed;
	}
	
	public void input(float delta) {
		float sensitivity = speed;
		
		if (Input.getKey(escKey)) {
			Input.setCursor(true);
			mouseLocked = false;
		}
		if (Input.getMouseDown(0)) {
			Input.setMousePosition(Window.getCenter());
			Input.setCursor(false);
			mouseLocked = true;
		}
		if (mouseLocked) {
			Vector2f deltaPos = Input.getMousePosition().sub(Window.getCenter());
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if (rotY) rotate(yAxis, toRadians(-deltaPos.getX() * sensitivity));
			
			if (rotX) rotate(getRight(), toRadians(deltaPos.getY() * sensitivity));
			
			if (rotY || rotX) Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
		}
		
	}
	
	private void rotate(Vector3f axis, float amount) {
		getTransform().rotate(axis, amount);
	}
	
	private Vector3f getRight() {
		return (Vector3f) getTransform().getRot().getRight().normalized();
	}
	
	private float toRadians(float angle) {
		return (float) Math.toRadians(angle);
	}
	@Override
	public void update(float delta) {
		
	}
	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
	}
	
}
