package graphics;

import java.util.List;

public class Mesh
{
	private List<Vertex>  vertices;
	private List<Integer> indices;

	public Vertex getVertex(int i) { return vertices.get(i); }
	public int getIndex(int i) { return indices.get(i); }
	public int getNumIndices() { return indices.size(); }

	public Mesh(List<Vertex> vertices, List<Integer> indices)
	{
		this.vertices = vertices;
		this.indices = indices;
	}
	
	public void draw(Renderer render, Matrix4f viewProjection, Matrix4f transform, Bitmap texture)
	{
		Matrix4f modelViewProjection = viewProjection.mul(transform);
		for(int a=0;a<indices.size();a+=3)
		{
			render.drawTriangle(vertices.get(indices.get(a)).transform(modelViewProjection), 
					vertices.get(indices.get(a + 1)).transform(modelViewProjection), 
					vertices.get(indices.get(a + 2)).transform(modelViewProjection), 
					texture);
		}
	}
}
