import java.io.FileNotFoundException;

import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.Model;
import com.sk.cache.wrappers.NpcDefinition;
import com.sk.cache.wrappers.loaders.ModelLoader;
import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;

public class NPCSaver {

	public NPCSaver(NpcDefinition npc) {

		Model model = new ModelLoader(npc.getLoader().getCacheSystem())
				.load(npc.modelIds[0]);
		//
		// System.out.println(npc.name);
		// System.out.println(model.getId());
		// System.out.println(model.vertexCount);

		new OBJCreator(model, npc.name);
	}

}
