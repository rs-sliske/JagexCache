package uk.sliske.viewer.wrappers;

public class MiniDef {
	private static boolean	showIDS	= true;

	public final int		id;
	public final String		name;

	public MiniDef(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public String toString() {
		if (showIDS) return name + " - " + id;
		return name;
	}

	public static void toggleIDS() {
		showIDS = !showIDS;
	}
}
