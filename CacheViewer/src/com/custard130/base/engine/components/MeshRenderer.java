package com.custard130.base.engine.components;



import com.custard130.base.engine.rendering.Material;
import com.custard130.base.engine.rendering.Mesh;
import com.custard130.base.engine.rendering.RenderingEngine;
import com.custard130.base.engine.rendering.Shader;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Material material;

	public MeshRenderer(Mesh mesh, Material material)
	{
		this.mesh = mesh;
		this.material = material;
	}

	@Override
	public void input(float delta) {}

	@Override
	public void update(float delta) {}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		shader.bind();
		shader.updateUniforms(getTransform(), material,renderingEngine);
		mesh.draw();
	}
}
