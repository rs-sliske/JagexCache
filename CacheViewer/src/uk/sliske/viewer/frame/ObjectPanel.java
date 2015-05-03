package uk.sliske.viewer.frame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uk.sliske.viewer.background.Search;
import uk.sliske.viewer.wrappers.Item;
import uk.sliske.viewer.wrappers.MiniDef;
import uk.sliske.viewer.wrappers.Obj;

import com.sk.cache.wrappers.loaders.ObjectDefinitionLoader;

public class ObjectPanel extends SearchPane {
	private static final long	serialVersionUID	= 1L;

	ObjectPanel() {
		super();
		listListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Item i = (Item) list.getSelectedValue();
				System.out.println("the object your have chosen to show is " + i.name);

				revalidate();
				repaint();
			}

		};
		finishInit();
	}

	protected void search_(final String s) {
		List<Integer> list = Search.get().objectSearch(s);
		ObjectDefinitionLoader loader = Search.get().objectLoader;
		List<MiniDef> list2 = new ArrayList<>();
		for (Integer i : list) {
			list2.add(new Obj(loader.load(i)));
		}
		currentData = list2;
		setList(list2);
	}
}
