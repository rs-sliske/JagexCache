package com.custard130.base.engine.components;

import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.core.input.Input;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;

public class KeyRot extends GameComponent {
	public static final Vector3f	yAxis	= new Vector3f(0, 1, 0);
	
	private static final float		SPEED	= 1.0F;
	private static final int		UP		= Input.KEY_UP;
	private static final int		DOWN	= Input.KEY_DOWN;
	private static final int		LEFT	= Input.KEY_LEFT;
	private static final int		RIGHT	= Input.KEY_RIGHT;
	
	private float					xSpeed;
	private float					ySpeed;
	private int						upKey;
	private int						downKey;
	private int						leftKey;
	private int						rightKey;
	
	public KeyRot() {
		this(SPEED);
	}
	
	public KeyRot(float speed) {
		this(speed, speed);
	}
	
	public KeyRot(float xSpeed, float ySpeed) {
		this(xSpeed, ySpeed, UP, DOWN, LEFT, RIGHT);
	}
	
	public KeyRot(int forward, int back, int left, int right) {
		this(SPEED, forward, back, left, right);
	}
	
	public KeyRot(float speed, int forward, int back, int left, int right) {
		this(speed, speed, forward, back, left, right);
	}
	
	public KeyRot(float xSpeed, float ySpeed, int upKey, int downKey, int leftKey, int rightKey) {
		super();
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.upKey = upKey;
		this.downKey = downKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}
	
	public void input(float delta) {
		float xRotAmt = delta * xSpeed;
		float yRotAmt = delta * ySpeed;
		
		if (Input.getKey(upKey)) rotate(getRight(), yRotAmt);
		if (Input.getKey(downKey)) rotate(getRight(), -yRotAmt);
		if (Input.getKey(leftKey)) rotate(yAxis, xRotAmt);
		if (Input.getKey(rightKey)) rotate(yAxis, -xRotAmt);
		
	}
	
	private void rotate(Vector3f axis, float amount) {
		getTransform().rotate(axis, amount);
	}
	
	private Vector3f getRight() {
		return (Vector3f) getTransform().getRot().getRight().normalized();
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
	}
	
}
