package com.custard130.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import com.custard130.base.engine.components.BaseLight;
import com.custard130.base.engine.components.Camera;
import com.custard130.base.engine.core.GameObject;
import com.custard130.base.engine.core.Mathf;
import com.custard130.base.engine.core.Transform;
import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.rendering.resourceManagement.MappedValues;

public class RenderingEngine extends MappedValues {
	private Camera						mainCamera;
	
	private BaseLight					activeLight;
	
	private HashMap<String, Integer>	samplerMap;
	
	private Shader						forwardAmbient;
	
	// more permanent
	private ArrayList<BaseLight>		lights;
	private Texture						tempTarget;
	
	private int							width;
	private int							height;

	private Camera	g_camera;
	private Transform g_transform;
	@SuppressWarnings("unused")
	private Mesh g_mesh;
	private Material	g_material;
	
	public RenderingEngine() {
		init();
		
		forwardAmbient = new Shader("forward-ambient");
		
		lights = new ArrayList<BaseLight>();
		
		samplerMap = new HashMap<String, Integer>();
		
		addVector("ambient", new Vector3f(0.5f));
		
		samplerMap.put("diffuse", 0);
		samplerMap.put("normalMap", 1);
		
		width = Window.getWidth() / 1;
		height = Window.getHeight() / 1;
		int dataSize = width * height * 4;
		byte[] bytes = new byte[dataSize];
		for(int i=0;i<dataSize;i++)
			bytes[i]=(byte)0;
		
		ByteBuffer data;
		//data = ByteBuffer.wrap(bytes);
		data=ByteBuffer.allocateDirect(dataSize);
		data.clear();
		
		tempTarget = new Texture(width, height, new ByteBuffer[] {data}, GL_COLOR_ATTACHMENT0, new float[] { GL_LINEAR },
				GL_COLOR_ATTACHMENT0);
		
		g_mesh =Mesh.plane(10);
		g_material = new Material();
		g_material.addTexture("diffuse", tempTarget);
		g_transform = new Transform();
		g_transform.setScale(new Vector3f(0.9f));
		g_camera = new Camera();
		GameObject cameraObject=new GameObject();
		cameraObject.addComponent(g_camera);
		g_camera.getTransform().rotate(new Vector3f(0,1,0),Mathf.toRadians(180.0f));
	}
	
	private void init() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_CLAMP);
		glEnable(GL_TEXTURE_2D);
	}
	
	public void render(GameObject object) {
		Window.bindAsRenderTarget(); 
	//	tempTarget.bindAsRenderTarget();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		object.renderAll(forwardAmbient, this);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for (BaseLight light : lights) {
			
			activeLight = light;
			object.renderAll(light.getShader(), this);
		}
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
		
		//Temp Render
		//Window.bindAsRenderTarget();
//		Camera temp = mainCamera;
//		mainCamera = g_camera;
//		glClearColor(0.0f,0.5f,0.5f,1.0f);
//		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//		forwardAmbient.bind();
//		forwardAmbient.updateUniforms(g_transform, g_material, this);
//		g_mesh.draw();
//		mainCamera = temp;
//		
	}
	
	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName,
			String uniformType) {
		
	}
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}
	
	public Camera getMainCamera() {
		return mainCamera;
	}
	
	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
	
	public void addLight(BaseLight light) {
		lights.add(light);
	}
	
	public BaseLight getActiveLight() {
		return activeLight;
	}
	
	public void addCamera(Camera camera) {
		mainCamera = camera;
	}
	
	public int getSampler(String key) {
		return samplerMap.get(key);
	}
	
}