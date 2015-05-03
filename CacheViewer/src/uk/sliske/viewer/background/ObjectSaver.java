package uk.sliske.viewer.background;

import com.sk.cache.wrappers.Model;
import com.sk.cache.wrappers.ObjectDefinition;
import com.sk.cache.wrappers.loaders.ModelLoader;

public class ObjectSaver {

	public ObjectSaver(ObjectDefinition object,String name, String folder) {

		Model model = new ModelLoader(object.getLoader().getCacheSystem())
				.load(object.modelIds[0][0]);
		//
		// System.out.println(npc.name);
		// System.out.println(model.getId());
		// System.out.println(model.vertexCount);

		new OBJCreator(model, name , folder);
	}

	

}
