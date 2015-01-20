package com.custard130.base.engine.rendering;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import static java.lang.Character.isWhitespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.custard130.base.engine.components.*;
import com.custard130.base.engine.core.*;
import com.custard130.base.engine.rendering.resourceManagement.ShaderResource;

public class Shader {
	private static HashMap<String, ShaderResource>	loadedShaders	= new HashMap<String, ShaderResource>();
	private ShaderResource							resource;
	
	private String									fileName;
	
	public Shader(String fileName) {
		this.fileName = fileName;
		resource = new ShaderResource();
		ShaderResource oldResource = loadedShaders.get(fileName);
		
		if (oldResource != null) {
			resource = oldResource;
		} else {
			resource = new ShaderResource();
			loadedShaders.put(fileName, resource);	
		
		String vertexShaderText = loadShader(fileName + ".vs");
		String fragmentShaderText = loadShader(fileName + ".fs");
		addVertexShader(vertexShaderText);
		addFragmentShader(fragmentShaderText);
		compileShader();
			parse(fragmentShaderText, vertexShaderText);
		}
		resource.addReference();
	}
	@Override
	protected void finalize() {
		if (resource.removeReference() && !fileName.isEmpty()) {
			loadedShaders.remove(fileName);
		}
	}
	public void bind() {
		glUseProgram(resource.getProgram());
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f MVPMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		for (int i = 0; i < resource.getUniformNames().size(); i++) {
			String uniformName = resource.getUniformNames().get(i);
			String uniformType = resource.getUniformTypes().get(i);
			String name = uniformName.substring(2);
			if (uniformType.equals("sampler2D")) {
				int samplerSlot = renderingEngine.getSampler(uniformName);
				material.getTexture(uniformName).bind(samplerSlot);
				setUniformi(uniformName, samplerSlot);
			} else if (uniformName.startsWith("T_")) {
				if (uniformName.equals("T_MVP"))
					setUniform(uniformName, MVPMatrix);
				else if (uniformName.equals("T_model"))
					setUniform(uniformName, worldMatrix);
				else
					throw new IllegalArgumentException(uniformName + "is not a valid component of transform");
			} else if (uniformName.startsWith("R_")) {
				// String name = uniformName.substring(2);
				if (uniformType.equals("vec3")) {
					setUniform(uniformName, renderingEngine.getVector(name));
				} else if (uniformType.equals("float")) {
					setUniformf(uniformName, renderingEngine.getFloat(name));
//				} else if (uniformType.equals("DirectionalLight")) {
//					setUniformDirectional(uniformName, (DirectionalLight) renderingEngine.getActiveLight());
//				} else if (uniformType.equals("SpotLight")) {
//					setUniformSpot(uniformName, (SpotLight) renderingEngine.getActiveLight());
//				} else if (uniformType.equals("PointLight")) {
//					setUniformPoint(uniformName, (PointLight) renderingEngine.getActiveLight());
				} else {
					renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
				}
			} else if (uniformName.startsWith("C_")) {
				if (name.equals("eyePos")) {
					setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
				}
				
			} else if (uniformName.startsWith("M_")) {
				if (uniformType.equals("vec3")) {
					setUniform(uniformName, material.getVector(name));
				} else if (uniformType.equals("float")) {
					setUniformf(uniformName, material.getFloat(name));
				}
				
			}
		}
	}
	
	private void setAttribLocation(String attribName, int location) {
		glBindAttribLocation(resource.getProgram(), location, attribName);
	}
	
	private void parse(String fragText, String vertText) {
		addAllAttributes(vertText);
		addAllUniforms(vertText);
		addAllUniforms(fragText);
	}
	
	private class GLSLStruct {
		public String	name;
		public String	type;
		
		@Override
		public String toString() {
			return type + " : " + name;
		}
	}
	
	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> res = new HashMap<String, ArrayList<GLSLStruct>>();
		final String KEYWORD = "struct";
		int startLocation = shaderText.indexOf(KEYWORD);
		while (startLocation != -1) {
			int nameBegin = startLocation + KEYWORD.length() + 1;
			int braceBegin = shaderText.indexOf("{", nameBegin);
			int braceEnd = shaderText.indexOf("};", braceBegin);
			
			String structName = shaderText.substring(nameBegin, braceBegin).trim();
			
			ArrayList<GLSLStruct> structComponents = new ArrayList<GLSLStruct>();
			
			int componentsSemicolonPos = shaderText.indexOf(";", braceBegin);
			
			while (componentsSemicolonPos != -1 && componentsSemicolonPos < braceEnd) {
				int componentNameStart = componentsSemicolonPos;
				while (!isWhitespace(shaderText.charAt(componentNameStart - 1))) {
					componentNameStart--;
				}
				String componentName = shaderText.substring(componentNameStart, componentsSemicolonPos);
				int componentTypeEnd = componentNameStart - 1;
				int componentTypeStart = componentTypeEnd;
				while (!isWhitespace(shaderText.charAt(componentTypeStart - 1))) {
					componentTypeStart--;
				}
				String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);
				
				GLSLStruct glslStruct = new GLSLStruct();
				glslStruct.name = componentName;
				glslStruct.type = componentType;
				// System.out.println(glslStruct);
				
				structComponents.add(glslStruct);
				
				componentsSemicolonPos = shaderText.indexOf(";", componentsSemicolonPos + 1);
			}
			res.put(structName, structComponents);
			startLocation = shaderText.indexOf(KEYWORD, startLocation + KEYWORD.length());
		}
		return res;
		
	}
	
	private void addAllAttributes(String shaderText) {
		final String ATTRIB_KEYWORD = "attribute";
		int attribStartLocation = shaderText.indexOf(ATTRIB_KEYWORD);
		int attribCount = 0;
		while (attribStartLocation != -1) {
			String token = shaderText.substring(attribStartLocation, shaderText.indexOf(";", attribStartLocation));
			// System.out.println(token);
			String[] tokens = token.split(" ");
			// System.out.println(tokens[2]);
			
			setAttribLocation(tokens[2], attribCount);
			attribCount++;
			
			attribStartLocation = shaderText.indexOf(ATTRIB_KEYWORD, attribStartLocation + ATTRIB_KEYWORD.length());
		}
	}
	
	private void addAllUniforms(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);
		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while (uniformStartLocation != -1) {
			String token = shaderText.substring(uniformStartLocation, shaderText.indexOf(";", uniformStartLocation));
			String[] tokens = token.split(" ");
			
			String uniformType = tokens[1];
			String uniformName = tokens[2];
			
			resource.getUniformNames().add(uniformName);
			resource.getUniformTypes().add(uniformType);
			addUniform(uniformName, uniformType, structs);
			
			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
		}
	}
	
	private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs) {
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);
		if (structComponents != null) {
			addThis = false;
			for (GLSLStruct struct : structComponents) {
				addUniform(uniformName + "." + struct.name, struct.type, structs);
			}
		}
		if (!addThis) return;
		
		int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);
		
		if (uniformLocation == 0xFFFFFFFF) {
			System.err.println("Error: Could not find uniform: " + uniformName);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		resource.getUniforms().put(uniformName, uniformLocation);
		
	}
	
	private void addVertexShader(String text) {
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	@SuppressWarnings("unused")
	private void addGeometryShader(String text) {
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	private void addFragmentShader(String text) {
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	private void compileShader() {
		glLinkProgram(resource.getProgram());
		
		if (glGetProgrami(resource.getProgram(), GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
		
		glValidateProgram(resource.getProgram());
		
		if (glGetProgrami(resource.getProgram(), GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
	}
	
	private void addProgram(String text, int type) {
		int shader = glCreateShader(type);
		
		if (shader == 0) {
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(resource.getProgram(), shader);
	}
	
	private static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		final String INCLUDE = "#include";
		
		try {
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;
			
			while ((line = shaderReader.readLine()) != null) {
				if (line.startsWith(INCLUDE)) {
					String includeName = line.substring(INCLUDE.length() + 2, line.length() - 1);
					shaderSource.append(loadShader(includeName));
				} else
					shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
	
	protected void setUniformi(String uniformName, int value) {
		glUniform1i(resource.getUniforms().get(uniformName), value);
	}
	
	protected void setUniformf(String uniformName, float value) {
		glUniform1f(resource.getUniforms().get(uniformName), value);
	}
	
	protected void setUniform(String uniformName, Vector3f value) {
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	protected void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4(resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
	}
	
	protected void setUniformBase(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
//	
//	protected void setUniformDirectional(String uniformName, DirectionalLight directionalLight) {
//		setUniformBase(uniformName + ".base", (BaseLight) directionalLight);
//		setUniform(uniformName + ".direction", directionalLight.getDirection());
//	}
//	
//	protected void setUniformPoint(String uniformName, PointLight pointLight) {
//		setUniformBase(uniformName + ".base", pointLight);
//		setUniformf(uniformName + ".atten.constant", pointLight.getAtten().getConstant());
//		setUniformf(uniformName + ".atten.linear", pointLight.getAtten().getLinear());
//		setUniformf(uniformName + ".atten.exponent", pointLight.getAtten().getExponent());
//		setUniform(uniformName + ".position", pointLight.getTransform().getPos());
//		setUniformf(uniformName + ".range", pointLight.getRange());
//	}
//	
//	protected void setUniformSpot(String uniformName, SpotLight spotLight) {
//		setUniformPoint(uniformName + ".pointLight", spotLight);
//		setUniform(uniformName + ".direction", spotLight.getDirection());
//		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
//	}
//	
}
