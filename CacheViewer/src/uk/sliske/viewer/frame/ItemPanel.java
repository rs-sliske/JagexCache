package uk.sliske.viewer.frame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uk.sliske.viewer.background.Search;
import uk.sliske.viewer.wrappers.Item;
import uk.sliske.viewer.wrappers.MiniDef;

import com.sk.cache.wrappers.loaders.ItemDefinitionLoader;

public class ItemPanel extends SearchPane {
	private static final long	serialVersionUID	= 1L;

	ItemPanel() {
		super();
		listListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Item i = (Item) list.getSelectedValue();
				System.out.println("the item your have chosen to show is " + i.name);
				infoPanel.removeAll();
				infoPanel.add(new ItemData(Search.get().itemLoader.load(i.id)));
				
				revalidate();
				repaint();
			}

		};
		finishInit();
	}

	protected void search_(final String s) {
		List<Integer> list = Search.get().itemSearch(s);
		ItemDefinitionLoader loader = Search.get().itemLoader;
		List<MiniDef> list2 = new ArrayList<>();
		for (Integer i : list) {
			list2.add(new Item(loader.load(i)));
		}
		currentData = list2;
		setList(list2);
	}
}
