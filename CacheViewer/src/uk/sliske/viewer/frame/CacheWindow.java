package uk.sliske.viewer.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CacheWindow extends JFrame{
	private static final long	serialVersionUID	= 1L;

	public CacheWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1080, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sliske's Runescape Cache Viewer");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("NPCs", null, new NpcPanel(), null);
		tabbedPane.addTab("Items", null, new ItemPanel(), null);
		tabbedPane.addTab("Objects", null, new ObjectPanel(), null);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}