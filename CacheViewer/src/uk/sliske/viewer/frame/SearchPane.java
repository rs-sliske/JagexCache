package uk.sliske.viewer.frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

import uk.sliske.viewer.graphics.Display;
import uk.sliske.viewer.wrappers.MiniDef;

public abstract class SearchPane extends JPanel {
	private static final long		serialVersionUID	= 1L;
	private JTextField				textField;
	protected Display				canvas;
	protected JList<Object>			list;
	private JPanel					panel;
	protected final JTabbedPane		tabbedPane;
	private JButton					btnToggleIds;
	protected Collection<MiniDef>	currentData;
	private JLabel					lblExactMatch;
	private JRadioButton			exactMatch;
	protected ListSelectionListener	listListener;
	protected final JPanel			infoPanel;
	private boolean					hasSearched			= false;

	SearchPane() {
		currentData = new ArrayList<>();

		setSize(1000, 700);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 25, 150, 0, 36, 720, 10 };
		gbl_panel.rowHeights = new int[] { 25, 25, 10, 25, 10, 465, 0, 10 };
		gbl_panel.columnWeights = new double[] { 0.1, 1.0, 0.0, Double.MIN_VALUE, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 10.0, Double.MIN_VALUE, Double.MIN_VALUE, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gbl_panel);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 5;
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

		infoPanel = new JPanel();
		tabbedPane.addTab("Info", infoPanel);

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
				hasSearched = true;
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);

		list = new JList<>();
		JScrollPane listPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		lblExactMatch = new JLabel("exact match");
		GridBagConstraints gbc_lblExactMatch = new GridBagConstraints();
		gbc_lblExactMatch.insets = new Insets(0, 0, 5, 5);
		gbc_lblExactMatch.gridx = 1;
		gbc_lblExactMatch.gridy = 3;
		add(lblExactMatch, gbc_lblExactMatch);

		exactMatch = new JRadioButton("");

		exactMatch.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (hasSearched) search(textField.getText());
			}
		});

		GridBagConstraints gbc_rdbtnExact = new GridBagConstraints();
		gbc_rdbtnExact.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnExact.gridx = 2;
		gbc_rdbtnExact.gridy = 3;
		add(exactMatch, gbc_rdbtnExact);

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 5;
		add(listPane, gbc_list);

		btnToggleIds = new JButton("toggle ids");
		btnToggleIds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MiniDef.toggleIDS();
				setList(currentData);
			}
		});
		GridBagConstraints gbc_btnToggleIds = new GridBagConstraints();
		gbc_btnToggleIds.gridx = 4;
		gbc_btnToggleIds.gridy = 6;
		add(btnToggleIds, gbc_btnToggleIds);

	}

	protected final void finishInit() {
		list.addListSelectionListener(listListener);
	}

	protected void setList(Collection<MiniDef> data) {
		list.setListData(data.toArray());
	}

	private void search(String s) {
		if (s.startsWith("*")) {
			if (exactMatch.isSelected()) {
				search_(s);
				return;
			}
			search_(s.substring(1));
			return;
		}
		if (!exactMatch.isSelected()) {
			search_(s);
			return;
		}
		search_("*" + s);

	}

	protected abstract void search_(final String s);
}
