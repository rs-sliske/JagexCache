package uk.sliske.viewer.graphics;
import static java.lang.Float.valueOf;
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import uk.sliske.viewer.background.Util;

public class OBJModel
{
	private ArrayList<Vector4f> positions;
	private ArrayList<Vector4f> texCoords;
	private ArrayList<Vector4f> normals;
	private ArrayList<OBJIndex> indices;
	private boolean hasTexCoords;
	private boolean hasNormals;
	
	public OBJModel(String fileName){
		this(new File(fileName));
	}

	public OBJModel(File file)
	{
		positions = new ArrayList<Vector4f>();
		texCoords = new ArrayList<Vector4f>();
		normals = new ArrayList<Vector4f>();
		indices = new ArrayList<OBJIndex>();
		hasTexCoords = false;
		hasNormals = false;

		BufferedReader meshReader = null;

		try
		{
			meshReader = new BufferedReader(new FileReader(file));
			String line;

			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					positions.add(new Vector4f(valueOf(tokens[1]),
							valueOf(tokens[2]),
							valueOf(tokens[3])));
				}
				else if(tokens[0].equals("vt"))
				{
					texCoords.add(new Vector4f(valueOf(tokens[1]),
							valueOf(tokens[2])));
				}
				else if(tokens[0].equals("vn"))
				{
					normals.add(new Vector4f(valueOf(tokens[1]),
							valueOf(tokens[2]),
							valueOf(tokens[3])));
				}
				else if(tokens[0].equals("f"))
				{
					for(int i = 0; i < tokens.length - 3; i++)
					{
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IndexedModel toIndexedModel()
	{
		IndexedModel result = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

		for(int i = 0; i < indices.size(); i++)
		{
			OBJIndex currentIndex = indices.get(i);

			Vector4f currentPosition = positions.get(currentIndex.vertexIndex);
			Vector4f currentTexCoord;
			Vector4f currentNormal;

			if(hasTexCoords)
				currentTexCoord = texCoords.get(currentIndex.texCoordIndex);
			else
				currentTexCoord = new Vector4f(0,0);

			if(hasNormals)
				currentNormal = normals.get(currentIndex.normalIndex);
			else
				currentNormal = new Vector4f(0,0,0);

			Integer modelVertexIndex = resultIndexMap.get(currentIndex);

			if(modelVertexIndex == null)
			{
				modelVertexIndex = result.getPositions().size();
				resultIndexMap.put(currentIndex, modelVertexIndex);

				result.getPositions().add(currentPosition);
				result.getTexCoords().add(currentTexCoord);
				if(hasNormals)
					result.getNormals().add(currentNormal);
			}

			Integer normalModelIndex = normalIndexMap.get(currentIndex.vertexIndex);

			if(normalModelIndex == null)
			{
				normalModelIndex = normalModel.getPositions().size();
				normalIndexMap.put(currentIndex.vertexIndex, normalModelIndex);

				normalModel.getPositions().add(currentPosition);
				normalModel.getTexCoords().add(currentTexCoord);
				normalModel.getNormals().add(currentNormal);
				normalModel.getTangents().add(new Vector4f(0,0,0));
			}

			result.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}

		if(!hasNormals)
		{
			normalModel.calcNormals();

			for(int i = 0; i < result.getPositions().size(); i++)
				result.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
		}

		normalModel.calcTangents();

		for(int i = 0; i < result.getPositions().size(); i++)
			result.getTangents().add(normalModel.getTangents().get(indexMap.get(i)));

		for(int i = 0; i < result.getTexCoords().size(); i++)
			result.getTexCoords().get(i).setY(1.0f - result.getTexCoords().get(i).getY());

		return result;
	}

	private OBJIndex parseOBJIndex(String token)
	{
		String[] values = token.split("/");

		OBJIndex result = new OBJIndex();
		result.vertexIndex = parseInt(values[0]) - 1;

		if(values.length > 1)
		{
			if(!values[1].isEmpty())
			{
				hasTexCoords = true;
				result.texCoordIndex = parseInt(values[1]) - 1;
			}

			if(values.length > 2)
			{
				hasNormals = true;
				result.normalIndex = parseInt(values[2]) - 1;
			}
		}

		return result;
	}
}
