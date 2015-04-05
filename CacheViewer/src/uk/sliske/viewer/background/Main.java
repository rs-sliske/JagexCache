package uk.sliske.viewer.background;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.sliske.viewer.frame.CacheWindow;
import uk.sliske.viewer.graphics.GraphicsMGR;

import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.NpcDefinition;
import com.sk.cache.wrappers.loaders.ItemDefinitionLoader;
import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;
import com.sk.cache.wrappers.loaders.ObjectDefinitionLoader;
import com.sk.cache.wrappers.loaders.QuestDefinitionLoader;

@SuppressWarnings("unused")
public class Main {
	public static final int					SLISKE_ID	= 14262;
	public static final int					VERAC_ID	= 2030;
	public static final int					GUTHAN_ID	= 2027;
	public static final int					TORAG_ID	= 2029;
	public static final int					DHAROK_ID	= 2026;
	public static final int					AHRIM_ID	= 2025;
	public static final int					AKRISAE_ID	= 14297;
	public static final int					KARIL_ID	= 2028;

	private final NpcDefinitionLoader		npcLoader;
	private final ItemDefinitionLoader		itemLoader;
	private final QuestDefinitionLoader		questLoader;
	private final ObjectDefinitionLoader	objectLoader;
	

	private Main() {
		CacheSystem cache = null;
		try {
			DataSource ds = new DataSource(DataSource.getDefaultCacheDirectory());
			cache = new CacheSystem(ds);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		npcLoader = new NpcDefinitionLoader(cache);
		itemLoader = new ItemDefinitionLoader(cache);
		questLoader = new QuestDefinitionLoader(cache);
		objectLoader = new ObjectDefinitionLoader(cache);
		
		new Search(cache);
		new CacheWindow();
		
//		System.out.println("searches - objectsearch, itemsearch, questsearch, npcsearch -");
//		System.out.println("         - args = the name/part of the name");
//		System.out.println("prints   - npc, item, object -");
//		System.out.println("         - args = the ids of the thing you want");
//		System.out.println("other:");
//		System.out
//				.println(" - models, will save an obj file of the npcs model in your home directory");
//		System.out.println("           models - args = the name of the npcs");
//		System.out.println("           models - args = the ids of the npcs");
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//		final Scanner scanner = new Scanner(System.in);
//		while (true) {
//			System.out.print("What task? : ");
//			final String task = scanner.nextLine();
//			if (task.equals("exit")) {
//				break;
//			}
//
//			System.out.print("args : ");
//			String arg = task + ", " + scanner.nextLine();
//			final String[] args = arg.split(", ");
//
//			handleArgs(npcLoader, objectLoader, itemLoader, questLoader, args);
//
//		}
//		scanner.close();
	}

	public static void main(String[] args) {
		new Main();
	}

	 int[] toIntArray(List<Integer> args) {
		int[] res = new int[args.size()];
		for (int i = 0; i < args.size(); i++) {
			res[i] = args.get(i);
		}
		return res;
	}

	 int[] toIntArray(String... args) {
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

	 void printNPCs(NpcDefinitionLoader loader, String... ids) {
		printNPCs(npcLoader, toIntArray(ids));
	}

	 void printItems(ItemDefinitionLoader loader, String... ids) {
		printItems(loader, toIntArray(ids));
	}

	void printObjects(ObjectDefinitionLoader loader, String... ids) {
		printObjects(loader, toIntArray(ids));
	}

	void printNPCs(NpcDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			System.out.println(loader.load(i));
		}
	}

	 void printItems(ItemDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			System.out.println(loader.load(i));
		}
	}

	 void printObjects(ObjectDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			System.out.println(loader.load(i));
		}
	}

	 void saveModels(NpcDefinitionLoader loader, String folder, int... ids) {
		for (int i : ids) {
			new NPCSaver(loader.load(i), folder);
		}
	}

	void saveModels(NpcDefinitionLoader loader, int... ids) {
		saveModels(loader, "", ids);
	}

	 ArrayList<Integer> itemSearch(ItemDefinitionLoader loader, String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (1 > 100000) break;
				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	 ArrayList<Integer> npcSearch(NpcDefinitionLoader loader, String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (i > 50000) break;
				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	 ArrayList<Integer> questSearch(QuestDefinitionLoader loader, String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (1 > 100000) break;
				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	 ArrayList<Integer> objectSearch(ObjectDefinitionLoader loader, String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (loader.canLoad(i)) {
				String s = loader.load(i).name;
				if (1 > 100000) break;

				if (check(res, s, names)) {
					res.add(i);
					System.out.println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	 boolean check(ArrayList<Integer> res, String s, String[] names) {
		try {
			for (String n : names) {
				if (n.startsWith("*")) {
					if (s.toLowerCase().equals(n.substring(1))) {
						return true;
					}
				} else
					if (s.toLowerCase().contains(n)) {
						return true;
					}
			}
		} catch (Exception e) {

		}
		return false;
	}

	private void showModel(final int id) {
		if (!npcLoader.canLoad(id)) return;
		NpcDefinition npc = npcLoader.load(id);

		new NPCSaver(npc, "");

		File f = new File(System.getProperty("user.home") + "\\models\\" + npc.name + ".obj");
		
		try {
			new GraphicsMGR(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	boolean handleArgs(NpcDefinitionLoader npc, ObjectDefinitionLoader obj, ItemDefinitionLoader item, QuestDefinitionLoader quest, String[] args) {
		if (args.length < 2) return false;
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
				break;
			}
			case "shows": {
				showModel(npcSearch(npc, values).get(0));
				break;
			}
			case "showi": {
				showModel(ids[0]);
				break;
			}
		}
		return true;
	}
}
