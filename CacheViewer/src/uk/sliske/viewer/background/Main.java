package uk.sliske.viewer.background;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import uk.sliske.viewer.frame.CacheWindow;

import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;


public class Main {
	private Main() {
		CacheSystem cache = null;
		try {
			DataSource ds = new DataSource(DataSource.getDefaultCacheDirectory());
			cache = new CacheSystem(ds);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		new Search(cache);
		new CacheWindow();
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

}
