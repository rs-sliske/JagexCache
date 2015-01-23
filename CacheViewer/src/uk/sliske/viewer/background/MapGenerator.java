package uk.sliske.viewer.background;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.RenderedImage;

import com.sk.cache.DataSource;
import com.sk.cache.fs.CacheSystem;
import com.sk.cache.wrappers.loaders.RegionLoader;
import com.sk.cache.wrappers.region.Region;
import com.sk.cache.wrappers.region.RegionUtil;
@SuppressWarnings("unused")
public class MapGenerator {
	
	private static final int AVAILABLE = 0x000000;
	private static final int BLOCKED = 0xFF0000;;

	private RenderedImage map;
	private int[][] tiles;

	private int width = 4000;
	private int height = 4000;

	private BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();

	public void start() {
		try {
			cacheStuff();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void cacheStuff() throws Exception {
		CacheSystem cacheSystem = new CacheSystem(
				DataSource.getDefaultCacheDirectory());
		RegionLoader loader = new RegionLoader(cacheSystem);
		int x0 = 0;
		int y0 = 0;
		while (loader.canLoad(x0)) {
			Region r = loader.load(x0);
			byte[][][] b = r.landscapeData;
			int rx = RegionUtil.getRegionX(r.getId());
			int ry = RegionUtil.getRegionY(r.getId());
			for (int x = 0; x < RegionUtil.REGION_WIDTH; x++) {
				for (int y = 0; y < RegionUtil.REGION_WIDTH; y++) {
					int xp = rx + x;
					if (xp > width)
						continue;
					int yp = ry + y;
					if (yp > height)
						continue;
					pixels[xp + (yp * RegionUtil.REGION_WIDTH)] = b[x][y][0];
				}
			}

			x0++;
		}

	}

	public BufferedImage getImage() {
		return image;
	}
}
