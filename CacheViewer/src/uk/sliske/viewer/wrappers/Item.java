package uk.sliske.viewer.wrappers;

import com.sk.cache.wrappers.ItemDefinition;

public class Item extends MiniDef {

	public Item(ItemDefinition item) {
		super(item.getId(), item.name);
	}

}

