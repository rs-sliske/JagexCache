package com.custard130.base.engine.rendering;

import java.util.HashMap;

import com.custard130.base.engine.rendering.resourceManagement.MappedValues;

public class Material extends MappedValues {
	private static final String			DEFAULT_TEXTURE	= "test.png";
	private static final String			DEFAULT_NORMAL	= "default_normal.jpg";
	
	private static HashMap<String, Material> materials = new HashMap<String, Material>();
	
	private HashMap<String, Texture>	textures;
	
	public Material() {
		super();
		textures = new HashMap<String, Texture>();
		addTexture("diffuse", DEFAULT_TEXTURE);
		addTexture("normalMap", DEFAULT_NORMAL);
		addFloat("specularIntensity", 1);
		addFloat("specularPower", 1);
	}
	public Material(String name) {
		this();
		materials.put(name,this);
	}
	
	public void addTexture(String key, String textureFileName){
		addTexture(key, new Texture(textureFileName));
	}
	
	public void addTexture(String name, Texture texture) {
		textures.put(name, texture);
	}
	
	public Texture getTexture(String name) {
		Texture res = textures.get(name);
		if (res != null) return res;
		if (name.equals("normalMap"))
			res = new Texture(DEFAULT_NORMAL);
		else
			res = new Texture(DEFAULT_TEXTURE);
		addTexture(name, res);
		return res;
	}

	public static Material get(String name){
		return materials.get(name);
	}
	
}
