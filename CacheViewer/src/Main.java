import java.io.FileNotFoundException;

import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;

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
			NpcDefinitionLoader loader = new NpcDefinitionLoader(cache);

			new NPCSaver(loader.load(SLISKE_ID));

			saveModels(loader, VERAC_ID, GUTHAN_ID, TORAG_ID, DHAROK_ID,
					AHRIM_ID, AKRISAE_ID, KARIL_ID);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	static void saveModels(NpcDefinitionLoader loader, int... ids) {
		for (int i : ids) {
			new NPCSaver(loader.load(i));
		}
	}

}
