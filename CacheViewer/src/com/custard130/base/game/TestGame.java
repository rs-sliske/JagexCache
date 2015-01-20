package com.custard130.base.game;

import com.custard130.base.engine.components.Camera;
import com.custard130.base.engine.components.KeyMove;
import com.custard130.base.engine.components.KeyRot;
import com.custard130.base.engine.core.Game;
import com.custard130.base.engine.core.GameObject;
import com.custard130.base.engine.core.Vector3f;

public class TestGame extends Game {

	public void init() {
		// addToRoot(TestStuff.lights());
		GameObject camera = camera();
		addToRoot(camera);
		
	}

	private GameObject camera() {
		GameObject cameraObject = new GameObject().addComponent(new Camera());
		cameraObject.addComponent(new KeyRot());
		cameraObject.addComponent(new KeyMove());
		cameraObject.getTransform().setPos(
				new Vector3f(-9.047198f, 8.761339f, -2.959814f));
		cameraObject.getTransform()
				.rotate(cameraObject.getTransform().getRot().getUp(),
						toRadians(-45.0f));
		cameraObject.getTransform()
				.rotate(cameraObject.getTransform().getRot().getRight(),
						toRadians(-30));
		return cameraObject;
	}

}
