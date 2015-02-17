package uk.sliske.viewer.wrappers;

import com.sk.cache.wrappers.NpcDefinition;

public class NPC extends MiniDef {

	public NPC(NpcDefinition npc) {
		super(npc.getId(), npc.name);
	}

}
