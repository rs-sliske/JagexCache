package uk.sliske.viewer.background;

public class Constants {

	public static final String	MODEL_PATH;
	public static final String	BANNED_CHARS	= "<>:\"/\\|?*";

	static {
		MODEL_PATH = System.getProperty("user.home") + "\\sliskeCache\\models\\";
		
	}

}
