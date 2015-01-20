package com.sk.cache.wrappers.region;

public interface Flagger {

	public void flag(Region r);

	public void unflag(Region r);

	public static final Flagger DEFAULT = new Flagger() {
		@Override
		public void flag(Region r) {
			
		}

		@Override
		public void unflag(Region r) {
			
		}
	};
}
