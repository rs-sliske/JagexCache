package uk.sliske.viewer.background;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import uk.sliske.viewer.graphics.IndexedModel;
import uk.sliske.viewer.graphics.Vector4f;

public class Util {
	public static boolean checkChar(final char c, final String s) {
		for (Character ch : s.toCharArray())
			if (ch == c) return true;
		return false;
	}
	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < data.length; i++)
			if (!data[i].equals("")) result.add(data[i]);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];
		
		for (int i = 0; i < data.length; i++)
			result[i] = data[i].intValue();
		
		return result;
	}
	

	public static boolean saveIndexedModel(final IndexedModel model, final String fileName) {
		final boolean success = false;
		new Thread("Exporting model to "+fileName){
			public void run(){
				saveIndexedModelToFile(model,fileName);
			}
		}.start();
		
		return success;
		
	}
	private static boolean saveIndexedModelToFile(IndexedModel model, String fileName) {
		try {
			@SuppressWarnings("resource")
			FileWriter file = new FileWriter(fileName);
			file.append("# indexed model generated on \n");
			for (Vector4f pos : model.getPositions()) {
				file.append("v");
				file.append(" " + pos.getX());
				file.append(" " + pos.getY());
				file.append(" " + pos.getZ());
				file.append("\n");
			}
			for (Vector4f tex : model.getTexCoords()) {
				file.append("vt");
				file.append(" " + tex.getX());
				file.append(" " + tex.getY());
				file.append("\n");
			}
			for (Vector4f normal : model.getNormals()) {
				file.append("vn");
				file.append(" " + normal.getX());
				file.append(" " + normal.getY());
				file.append(" " + normal.getZ());
				file.append("\n");
			}
			for (int i = 0; i < model.getIndices().size(); i ++) {
				file.append("f");
				//for (int j = 0; j < 9; j++)
					file.append(" " + model.getIndices().get(i));
				file.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
