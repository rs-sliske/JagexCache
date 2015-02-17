package uk.sliske.viewer.frame;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;

import uk.sliske.viewer.background.Search;
import uk.sliske.viewer.graphics.Display;
import uk.sliske.viewer.graphics.GraphicsMGR;
import uk.sliske.viewer.wrappers.MiniDef;
import uk.sliske.viewer.wrappers.NPC;

import javax.swing.JTabbedPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NpcPanel extends JPanel {
	private JTextField		textField;
	private Display			canvas;
	private JList<Object>	list;
	private Button			button;
	private JPanel			panel;
	private JTabbedPane		tabbedPane;
	private JButton btnToggleIds;
	private Collection<NPC> currentData;

	NpcPanel() {
		currentData = new ArrayList<>();
		
		setSize(1000, 700);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 25, 150, 0, 36, 720, 10 };
		gbl_panel.rowHeights = new int[] { 25, 25, 10, 500, 0, 10 };
		gbl_panel.columnWeights = new double[] { 0.1, 1.0, 0.0, Double.MIN_VALUE, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 10.0, Double.MIN_VALUE, Double.MIN_VALUE, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gbl_panel);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 4;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		panel.add(tabbedPane);

		canvas = new Display(800, 600);
		canvas.setBackground(Color.BLACK);
		canvas.createBufferStrategy(1);		
		
		tabbedPane.addTab("View", null, canvas, null);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				search(textField.getText());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);

		list = new JList<>();
		JScrollPane listPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);		
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 3;
		add(listPane, gbc_list);

		button = new Button("Show NPC");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NPC o = (NPC) list.getSelectedValue();
				System.out.println("the npc your have chosen to show is " + o.name);
				try {
					GraphicsMGR.showModel(o.id, canvas);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 4;
		add(button, gbc_button);
		
		btnToggleIds = new JButton("toggle ids");
		btnToggleIds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MiniDef.toggleIDS();
				setList(currentData);
			}
		});
		GridBagConstraints gbc_btnToggleIds = new GridBagConstraints();
		gbc_btnToggleIds.gridx = 4;
		gbc_btnToggleIds.gridy = 4;
		add(btnToggleIds, gbc_btnToggleIds);

	}

	public void setList(Collection<NPC> data) {
		list.setListData(data.toArray());
	}

	private void search(String s) {
		List<Integer> list = Search.get().npcSearch(s);
		NpcDefinitionLoader loader = Search.get().npcLoader;
		List<NPC> list2 = new ArrayList<>();
		for (Integer i : list) {
			list2.add(new NPC(loader.load(i)));
		}
		currentData=list2;
		setList(list2);
	}

}
