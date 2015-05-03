package uk.sliske.viewer.background;

import java.io.FileNotFoundException;

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

}
