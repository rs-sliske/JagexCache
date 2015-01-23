package uk.sliske.viewer.background;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

			handleArgs(npcLoader, objectLoader, itemLoader, questLoader, args);

			itemSearch(itemLoader, "*clay");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	static int[] toIntArray(List<Integer> args) {
		int[] res = new int[args.size()];
		for (int i = 0; i < args.size(); i++) {
			res[i] = args.get(i);
		}
		return res;
	}

	static int[] toIntArray(String... args) {
		ArrayList<Integer> i = new ArrayList<>();
		for (String s : args) {
			try {
				int id = Integer.valueOf(s);
				i.add(id);
			} catch (Exception e) {

			}
		}
		return toIntArray(i);
	}

	static void printNPCs(NpcDefinitionLoader loader, String... ids) {
		printNPCs(loader, toIntArray(ids));
	}

	static void printItems(ItemDefinitionLoader loader, String... ids) {
		printItems(loader, toIntArray(ids));
	}

	static void printObjects(ObjectDefinitionLoader loader, String... ids) {
		printObjects(loader, toIntArray(ids));
	}

	static void printNPCs(NpcDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			System.out.println(loader.load(i));
		}
	}

	static void printItems(ItemDefinitionLoader loader, int... ids) {
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
				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
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
				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
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
				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
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

				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	static boolean check(ArrayList<Integer> res, String s, String[] names) {
		try {
			for (String n : names) {
				if (n.startsWith("*")) {
					if (s.toLowerCase().equals(n.substring(1))) {
						return true;
					}
				} else if (s.toLowerCase().contains(n)) {
					return true;
				}
			}
		} catch (Exception e) {

		}
		return false;
	}

	static boolean handleArgs(NpcDefinitionLoader npc,
			ObjectDefinitionLoader obj, ItemDefinitionLoader item,
			QuestDefinitionLoader quest, String[] args) {
		if (args.length < 2)
			return false;
		String key = args[0];
		String[] values = new String[(args.length - 1)];
		for (int i = 0; i < args.length - 1; i++)
			values[i] = args[i + 1];
		int[] ids = toIntArray(values);
		switch (key) {
		// search
		case "npcsearch": {
			npcSearch(npc, values);
			break;
		}
		case "objectsearch": {
			objectSearch(obj, values);
			break;
		}
		case "itemsearch": {
			itemSearch(item, values);
			break;
		}
		case "questsearch": {
			questSearch(quest, values);
			break;
		}

		// print
		case "npc": {
			printNPCs(npc, ids);
			break;
		}
		case "item": {
			printItems(item, ids);
			break;
		}
		case "object": {
			printObjects(obj, ids);
			break;
		}

		// models
		case "models": {
			saveModels(npc, toIntArray(npcSearch(npc, values)));
			break;
		}
		case "modeli": {
			saveModels(npc, ids);
		}
		}
		return true;
	}
}
