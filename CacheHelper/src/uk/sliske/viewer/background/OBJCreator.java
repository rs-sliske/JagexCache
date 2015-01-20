package uk.sliske.viewer.background;

import com.sk.cache.wrappers.Model;

public class OBJCreator {

	private FileSaver file;

	public OBJCreator(Model model, String name, String folder) {
		@SuppressWarnings("unused")
		String path = folder + "\\";
		if (folder.isEmpty())
			path = "";

		file = new FileSaver(name + ".obj");

		for (int i = 0; i < model.vertexCount; i++) {
			String l = "v " + model.verticesX[i] + " " + model.verticesY[i]
					+ " " + model.verticesZ[i] + "\n";
			file.append(l);
		}
		for (int i = 0; i < model.triangleCount; i++) {
			String l = "f " + (model.trianglesC[i] + 1) + " "
					+ (model.trianglesB[i] + 1) + " "
					+ (model.trianglesA[i] + 1) + "\n";
			file.append(l);
		}
		file.close();
	}

	public OBJCreator(Model model, String name) {
		this(model,name,"");
	}
}
