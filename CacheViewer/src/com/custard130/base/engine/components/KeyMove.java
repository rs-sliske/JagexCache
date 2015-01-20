package com.custard130.base.engine.components;

import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.core.input.Input;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;

public class KeyMove extends GameComponent {
	
	private static final float	SPEED	= 10.0F;
	private static final int	FORWARD	= Input.KEY_W;
	private static final int	BACK	= Input.KEY_S;
	private static final int	LEFT	= Input.KEY_A;
	private static final int	RIGHT	= Input.KEY_D;
	private static final int	UP		= Input.KEY_E;
	private static final int	DOWN	= Input.KEY_Q;
	
	private float				xSpeed;
	private float				ySpeed;
	private float				zSpeed;
	
	private int					forwardKey;
	private int					backKey;
	private int					leftKey;
	private int					rightKey;
	private int					upKey;
	private int					downKey;
	
	public KeyMove() {
		this(SPEED);
	}
	
	public KeyMove(float speed) {
		this(speed, speed,speed);
	}
	
	public KeyMove(float xSpeed, float ySpeed,float zSpeed) {
		this(xSpeed, ySpeed,zSpeed, FORWARD, BACK, LEFT, RIGHT, UP, DOWN);
	}
	
	public KeyMove(int forward, int back, int left, int right, int up, int down) {
		this(SPEED, forward, back, left, right, up, down);
	}
	
	public KeyMove(float speed, int forward, int back, int left, int right, int up, int down) {
		this(speed, speed,speed, forward, back, left, right, up, down);
	}
	
	public KeyMove(float xSpeed, float ySpeed, float zSpeed,int forwardKey, int backKey, int leftKey, int rightKey, int upKey, int downKey) {
		super();
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
		
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
	}
	
	public void input(float delta) {
		float xMovAmt = xSpeed * delta;
		float yMovAmt = ySpeed * delta;
		float zMovAmt = zSpeed * delta;
		
		if (Input.getKey(forwardKey)) move(getForward(), zMovAmt);
		if (Input.getKey(backKey)) move(getForward(), -zMovAmt);
		if (Input.getKey(leftKey)) move(getRight(), -xMovAmt);
		if (Input.getKey(rightKey)) move(getRight(), xMovAmt);
		if (Input.getKey(upKey)) move(getUp(), yMovAmt);
		if (Input.getKey(downKey)) move(getUp(), -yMovAmt);
	}
	
	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
	
	private Vector3f getUp() {
		return getTransform().getRot().getUp().normalized();
	}
	
	private Vector3f getRight() {
		return (Vector3f) getTransform().getRot().getRight().normalized();
	}
	
	private Vector3f getForward() {
		return (Vector3f) getTransform().getRot().getForward().normalized();
	}
	
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
	}
	
}
