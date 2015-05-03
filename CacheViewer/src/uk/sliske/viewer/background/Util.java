package uk.sliske.viewer.background;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.sk.cache.wrappers.NpcDefinition;
import com.sk.cache.wrappers.ObjectDefinition;

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
	public static File modelfilemgr_npc(final int npcID) {
		if (!Search.get().npcLoader.canLoad(npcID)) return null;
		NpcDefinition npc = Search.get().npcLoader.load(npcID);
		String s = npc.name;
		StringBuilder name = new StringBuilder(Constants.MODEL_PATH);
		char[] ch = s.toCharArray();
		for (Character c : ch) 
			if (!Util.checkChar(c, Constants.BANNED_CHARS)) 
				name.append(c);
		
		name.append(" ").append(npcID).append(".obj");
		File f = new File(name.toString());
		if (!f.exists())
			new NPCSaver(npc, name.substring(Constants.MODEL_PATH.length(), name.length() - 4), "");
		return f;
	}
	public static File modelfilemgr_obj(final int objectID) {
		if (!Search.get().objectLoader.canLoad(objectID)) return null;
		ObjectDefinition npc = Search.get().objectLoader.load(objectID);
		String s = npc.name;
		StringBuilder name = new StringBuilder(Constants.MODEL_PATH);
		char[] ch = s.toCharArray();
		for (Character c : ch) 
			if (!Util.checkChar(c, Constants.BANNED_CHARS)) 
				name.append(c);
		
		name.append(" ").append(objectID).append(".obj");
		File f = new File(name.toString());
		if (!f.exists())
			new ObjectSaver(npc, name.substring(Constants.MODEL_PATH.length(), name.length() - 4), "");
		return f;
	}
}
