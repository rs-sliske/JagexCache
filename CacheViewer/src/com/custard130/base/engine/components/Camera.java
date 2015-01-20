package com.custard130.base.engine.components;

import com.custard130.base.engine.Debug;
import com.custard130.base.engine.core.CoreEngine;
import com.custard130.base.engine.core.Matrix4f;
import com.custard130.base.engine.core.Vector2f;
import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.core.input.Input;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;
import com.custard130.base.engine.rendering.Window;

public class Camera extends GameComponent {
	public static final Vector3f	yAxis	= new Vector3f(0, 1, 0);

	private Matrix4f				projection;

	public Camera(float fov, float aspect, float zNear, float zFar) {
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}

	public Camera() {
		this((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);
	}

	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = getTransform().getRot().toRotationMatrix();
		Vector3f cameraPos = (Vector3f) getTransform().getTransformedPos().mul(-1);

		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(),
				cameraPos.getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}

	boolean		mouseLocked		= false;
	Vector2f	centerPosition	= new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

	@Override
	public void addToEngine(CoreEngine engine) {
		Debug.println("camera added");
		engine.getRenderingEngine().addCamera(this);
	}

	public void input0(float delta) {
		float sensitivity = 0.5f;
		float movAmt = 10 * delta;
		float rotAmt = delta;

		if (Input.getKey(Input.KEY_ESCAPE)) {
			Input.setCursor(true);
			mouseLocked = false;
		}
		if (Input.getMouseDown(0)) {
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		if (Input.getKey(Input.KEY_W))
			move(getForward(), movAmt);
		if (Input.getKey(Input.KEY_S))
			move(getForward(), -movAmt);
		if (Input.getKey(Input.KEY_A))
			move(getRight(), -movAmt);
		if (Input.getKey(Input.KEY_D))
			move(getRight(), movAmt);

		if (mouseLocked) {
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if (rotY)
				rotate(yAxis,toRadians(-deltaPos.getX() * sensitivity));

			if (rotX)
				rotate(getRight(), toRadians(deltaPos.getY() * sensitivity));

			if (rotY || rotX)
				Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
		}

		if (Input.getKey(Input.KEY_UP))
			rotate(getRight(), rotAmt);
		if (Input.getKey(Input.KEY_DOWN))
			rotate(getRight(), -rotAmt);
		if (Input.getKey(Input.KEY_LEFT))
			rotate(yAxis, rotAmt);
		if (Input.getKey(Input.KEY_RIGHT))
			rotate(yAxis, -rotAmt);

		if (Input.getKey(Input.KEY_P)) {
			Debug.println("position " + getPos());
			Debug.println("rotation " + getForward());
		}
	}

	private void rotate(Vector3f axis, float amount) {
		getTransform().rotate(axis, amount);
	}

	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

	public void setPos(Vector3f pos) {
		getTransform().setPos(pos);
	}

	public Vector3f getPos() {
		return getTransform().getPos();
	}

	private Vector3f getRight() {
		return (Vector3f) getTransform().getRot().getRight().normalized();
	}
	
	private Vector3f getForward() {
		return (Vector3f) getTransform().getRot().getForward().normalized();
	}
	
	private float toRadians(float angle){
		return (float)Math.toRadians(angle);
	}

	@Override
	public void input(float delta) {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
	}

}
