package com.custard130.base.engine.components;

import com.custard130.base.engine.core.CoreEngine;
import com.custard130.base.engine.core.Vector3f;
import com.custard130.base.engine.rendering.Shader;

public abstract class BaseLight extends GameComponent
{
	private Vector3f color;
	private float intensity;
	private Shader shader;
	
	public BaseLight(Vector3f color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}	
	public Vector3f getColor()
	{
		return color;
	}

	public void setColor(Vector3f color)
	{
		this.color = color;
	}

	public float getIntensity()
	{
		return intensity;
	}

	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}
	protected void setShader(Shader shader){
		this.shader=shader;
	}

	public Shader getShader(){
		return shader;
	}
	@Override
	public void addToEngine(CoreEngine engine){
		engine.getRenderingEngine().addLight(this);
	}
	
}
