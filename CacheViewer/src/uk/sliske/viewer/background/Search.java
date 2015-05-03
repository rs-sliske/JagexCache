package uk.sliske.viewer.background;

import java.util.ArrayList;

import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.loaders.ImageLoader;
import com.sk.cache.wrappers.loaders.ItemDefinitionLoader;
import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;
import com.sk.cache.wrappers.loaders.ObjectDefinitionLoader;
import com.sk.cache.wrappers.loaders.QuestDefinitionLoader;

public class Search {
	
	private static boolean print = false;

	public final NpcDefinitionLoader	npcLoader;
	public final ItemDefinitionLoader	itemLoader;
	public final QuestDefinitionLoader	questLoader;
	public final ObjectDefinitionLoader	objectLoader;
	public final ImageLoader imageLoader;
	
	private static int maxSearchDepth = 1000000;

	private static Search				s;

	public static Search get() {
		return s;
	}

	public Search(CacheSystem cache) {
		s = this;
		npcLoader = new NpcDefinitionLoader(cache);
		itemLoader = new ItemDefinitionLoader(cache);
		questLoader = new QuestDefinitionLoader(cache);
		objectLoader = new ObjectDefinitionLoader(cache);
		imageLoader = new ImageLoader(cache);
	}

	public ArrayList<Integer> itemSearch(String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < 100000) {
			if (itemLoader.canLoad(i)) {
				String s = itemLoader.load(i).name;
				if (i > maxSearchDepth) break;
				if (check(res, s, names)) {
					res.add(i);
					println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	public ArrayList<Integer> npcSearch(String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < maxSearchDepth) {
			if (npcLoader.canLoad(i)) {
				String s = npcLoader.load(i).name;
				if (i > 50000) break;
				if (check(res, s, names)) {
					res.add(i);
					println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	public ArrayList<Integer> questSearch(String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < maxSearchDepth) {
			if (questLoader.canLoad(i)) {
				String s = questLoader.load(i).name;
				if (i > 100000) break;
				if (check(res, s, names)) {
					res.add(i);
					println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	public ArrayList<Integer> objectSearch(String... names) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String s : names) {
			s.toLowerCase();
		}
		int i = 0;
		while (i < maxSearchDepth) {
			if (objectLoader.canLoad(i)) {
				String s = objectLoader.load(i).name;
				if (i > 100000) break;

				if (check(res, s, names)) {
					res.add(i);
					println(i + " : " + s);
				}
			}
			i++;
		}
		return res;
	}

	private boolean check(ArrayList<Integer> res, String s, String[] names) {
		try {
			for (String n : names) {
				if (n.startsWith("*")) {
					if (s.toLowerCase().equals(n.substring(1))) {
						return true;
					}
				} else
					if (n.endsWith("*")) {
						if (s.toLowerCase().equals(n.substring(0, s.length() - 1))) {
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
	private static void println(String s){
		if(print)System.out.println(s);
	}
	public static void shouldPrint(boolean b){
		print=b;
	}
}
