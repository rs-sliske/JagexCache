package uk.sliske.viewer.background;

import java.io.FileNotFoundException;
import java.util.ArrayList;



import com.custard130.util.graphics.Window;
import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.loaders.ItemDefinitionLoader;
import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;
import com.sk.cache.wrappers.loaders.ObjectDefinitionLoader;
import com.sk.cache.wrappers.loaders.QuestDefinitionLoader;

@SuppressWarnings("unused")
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
			ItemDefinitionLoader itemLoader = new ItemDefinitionLoader(cache);
			QuestDefinitionLoader questLoader = new QuestDefinitionLoader(cache);
			ObjectDefinitionLoader objectLoader = new ObjectDefinitionLoader(
					cache);
//			ArrayList<Integer> banks = objectSearch(objectLoader, "deposit box");
//			StringBuilder s = new StringBuilder();
//			for(int i :banks){
//				if(!objectLoader.load(i).name.contains("losed")){
//					s.append(i);
//					s.append(", ");
//				}
//			}
//			System.out.println(banks);
//			System.out.println(s.toString());
			MapGenerator map = new MapGenerator();
			map.start();
			Window.addWindowWithImage("map", "map", map.getImage());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	static void printNPCs(NpcDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			System.out.println(loader.load(i));
		}
	}

	static void printObjects(ObjectDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			System.out.println(loader.load(i));
		}
	}

	static void saveModels(NpcDefinitionLoader loader, String folder,
			int... ids) {
		for (int i : ids) {
			new NPCSaver(loader.load(i), folder);
		}
	}

	static void saveModels(NpcDefinitionLoader loader, int... ids) {
		saveModels(loader, "", ids);
	}

	static ArrayList<Integer> itemSearch(ItemDefinitionLoader loader,
			String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (1 > 100000)
					break;
				try {
					for (String n : names) {
						if (s.toLowerCase().contains(n)) {
							res.add(i);
							System.out.println(i + " : " + s);
						}
					}
				} catch (Exception e) {
				}
			}
			i++;
		}
		return res;
	}

	static ArrayList<Integer> npcSearch(NpcDefinitionLoader loader,
			String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (i > 50000)
					break;
				try {
					for (String n : names) {
						if (s.toLowerCase().contains(n)) {
							res.add(i);
							System.out.println(i + " : " + s);						
						}
					}
				} catch (Exception e) {

				}
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
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (1 > 100000)
					break;
				try {
					for (String n : names) {
						if (s.toLowerCase().contains(n)) {
							res.add(i);
							System.out.println(i + " : " + s);						
						}
					}
				} catch (Exception e) {

				}
			}
			i++;
		}
		return res;
	}
	
	static ArrayList<Integer> objectSearch(ObjectDefinitionLoader loader,
			String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (1 > 100000)
					break;
				try {
					for (String n : names) {
						if (s.toLowerCase().contains(n)) {
							res.add(i);
							System.out.println(i + " : " + s);						
						}
					}
				} catch (Exception e) {

				}
			}
			i++;
		}
		return res;
	}

	
}
