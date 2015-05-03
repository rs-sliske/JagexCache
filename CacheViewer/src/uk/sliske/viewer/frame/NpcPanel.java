package uk.sliske.viewer.frame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uk.sliske.viewer.background.Search;
import uk.sliske.viewer.background.Util;
import uk.sliske.viewer.graphics.GraphicsMGR;
import uk.sliske.viewer.wrappers.MiniDef;
import uk.sliske.viewer.wrappers.NPC;

import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;

public class NpcPanel extends SearchPane {
	private static final long	serialVersionUID	= 1L;
	private final NpcSourcePanel srcpanel;
	NpcPanel() {
		super();
		srcpanel = new NpcSourcePanel();
		listListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				final NPC i = (NPC) list.getSelectedValue();
				System.out.println("the npc your have chosen to show is " + i.name);
				try {
					GraphicsMGR.showModel_npc(i.id, canvas);
					srcpanel.replaceText(Util.modelfilemgr_npc(i.id));
				} catch (IOException e) {
					e.printStackTrace();
				}
				revalidate();
				repaint();
			}
		};
		tabbedPane.add("src", srcpanel);
		finishInit();
	}

	protected void search_(final String s) {
		List<Integer> list = Search.get().npcSearch(s);
		NpcDefinitionLoader loader = Search.get().npcLoader;
		List<MiniDef> list2 = new ArrayList<>();
		for (Integer i : list) {
			list2.add(new NPC(loader.load(i)));
		}
		currentData = list2;
		setList(list2);
	}
}
