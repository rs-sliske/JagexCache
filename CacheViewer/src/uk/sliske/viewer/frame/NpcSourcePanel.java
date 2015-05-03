package uk.sliske.viewer.frame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NpcSourcePanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private final JTextArea textArea;
	/**
	 * Create the panel.
	 */
	public NpcSourcePanel() {
		setLayout(null);		
		textArea = new JTextArea();
		textArea.setText("");
		add(textArea);
	}
	
	protected void replaceText(File newText){
		try {
			@SuppressWarnings("resource")
			final Scanner scanner = new Scanner(newText);
			textArea.setText("");
			while(scanner.hasNextLine()){
				textArea.append(scanner.nextLine());
			}			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
