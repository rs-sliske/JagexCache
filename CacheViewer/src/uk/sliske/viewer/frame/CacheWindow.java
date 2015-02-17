package uk.sliske.viewer.frame;

import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import uk.sliske.viewer.background.Search;
import uk.sliske.viewer.graphics.Display;
import uk.sliske.viewer.graphics.GraphicsMGR;
import uk.sliske.viewer.wrappers.NPC;

import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;

public class CacheWindow extends JFrame{

	public CacheWindow() {
		setBounds(100, 100, 1080, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		final JPanel panel = new NpcPanel();
		tabbedPane.addTab("NPCs", null, panel, null);
		setVisible(true);
	}
}