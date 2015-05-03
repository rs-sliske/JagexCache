package uk.sliske.viewer.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class CacheWindow extends JFrame{
	private static final long	serialVersionUID	= 1L;

	public CacheWindow() {
		setBounds(100, 100, 1080, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("NPCs", null, new NpcPanel(), null);
		tabbedPane.addTab("Items", null, new ItemPanel(), null);
		
		setVisible(true);
	}
}