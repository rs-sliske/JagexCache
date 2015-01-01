import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.ItemDefinition;
import com.sk.cache.wrappers.QuestDefinition;
import com.sk.cache.wrappers.loaders.ItemDefinitionLoader;
import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;
import com.sk.cache.wrappers.loaders.QuestDefinitionLoader;

public class Main {
	public static final int SLISKE_ID = 14262;
	public static final int VERAC_ID = 2030;
	public static final int GUTHAN_ID = 2027;
	public static final int TORAG_ID = 2029;
	public static final int DHAROK_ID = 2026;
	public static final int AHRIM_ID = 2025;
	public static final int AKRISAE_ID = 14297;
	public static final int KARIL_ID = 2028;

	public static void main(String[] args) {

		try {
			DataSource ds = new DataSource(
					DataSource.getDefaultCacheDirectory());
			CacheSystem cache = new CacheSystem(ds);
			NpcDefinitionLoader npcLoader = new NpcDefinitionLoader(cache);
			//
			// new NPCSaver(loader.load(SLISKE_ID));
			//
			// saveModels(loader, VERAC_ID, GUTHAN_ID, TORAG_ID, DHAROK_ID,
			// AHRIM_ID, AKRISAE_ID, KARIL_ID);

			ItemDefinitionLoader itemLoader = new ItemDefinitionLoader(cache);
			//itemSearch(itemLoader, "seed");
			
			ItemDefinition id = itemLoader.load(32625);
			//System.out.println(id.toString());
			
			QuestDefinitionLoader questLoader= new QuestDefinitionLoader(cache);
			QuestDefinition quest = questLoader.load(175);
			//System.out.println(quest);
			
			//questSearch(questLoader, "fairy");
			//System.out.println(questLoader.load(154));
			//System.out.println(questLoader.load(175));
			
			//System.out.println(quest.scriptId[0]);
			new QuestDump(questLoader, true);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	static void saveModels(NpcDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			new NPCSaver(loader.load(i));
		}
	}

	static ArrayList<Integer> itemSearch(ItemDefinitionLoader loader,
			String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 32400;
		while (true) {
			String s = loader.load(i).name;
			if(1>100000)break;
			try {
				for (String n : names) {
					if (s.toLowerCase().contains(n)) {
						res.add(i);
						System.out.println(i + " : " + s);
					}else{
						//System.out.println(i + " : " + s);
					}
				}
			} catch (Exception e) {

			}
			i++;
		}
		return res;
	}
	
	
	static ArrayList<Integer> questSearch(QuestDefinitionLoader loader,
			String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (true) {
			String s = loader.load(i).name;
			if(1>100000)break;
			try {
				for (String n : names) {
					if (s.toLowerCase().contains(n)) {
						res.add(i);
						System.out.println(i + " : " + s);
					}else{
						//System.out.println(i + " : " + s);
					}
				}
			} catch (Exception e) {

			}
			i++;
		}
		return res;
	}
	
}
