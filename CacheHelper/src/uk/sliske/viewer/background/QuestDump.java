package uk.sliske.viewer.background;

import java.util.ArrayList;

import com.sk.cache.wrappers.QuestDefinition;
import com.sk.cache.wrappers.Script;
import com.sk.cache.wrappers.loaders.QuestDefinitionLoader;
import com.sk.cache.wrappers.loaders.ScriptLoader;

public class QuestDump {

	public QuestDump(final QuestDefinitionLoader loader, final boolean forScript) {
		ArrayList<QuestDefinition> quests = new ArrayList<QuestDefinition>();
		int i = 0;
		while (loader.canLoad(i)) {
			quests.add(loader.load(i));
			i++;
		}
		System.out.println(quests.size() + " quests loaded");
		FileSaver file = new FileSaver("QuestDump.txt",
				"C:\\users\\Robert\\Desktop\\");
		ScriptLoader scriptLoader = new ScriptLoader(loader.getCacheSystem());

		if (forScript)
			saveForScripts(file, quests, scriptLoader, false);
	}

	private void saveForScripts(FileSaver file,
			ArrayList<QuestDefinition> quests, ScriptLoader scriptLoader,
			boolean scripts) {
		String name1 = "";
		ArrayList<QuestDefinition> quests0 = new ArrayList<QuestDefinition>();
		for (QuestDefinition q : quests) {
			
			try {
				String name0 = q.name;
				int varpBit0 = 0;
				int endValue = 0;
				if (q.configId != null) {
					varpBit0 = q.configId[0];
					endValue = q.configEndValue[0];
				} else if (q.scriptId != null) {
					if(!scripts){
						quests0.add(q);
						continue;
					}
					Script s = scriptLoader.load(q.scriptId[0]);
					varpBit0 = s.configId;
					endValue = q.scriptEndValue[0];
				}
				System.out.println(q);
				char[] name = name0.toCharArray();
				ArrayList<Character> newName = new ArrayList<Character>();
				char prevChar = ' ';
				for (Character c : name) {
					if (Character.isLetterOrDigit(c)) {
						newName.add(Character.toUpperCase(c));
						prevChar = c;
					}
					if (c.equals(' ')) {
						if (prevChar != '_')
							newName.add('_');
						prevChar = '_';
					}

				}
				char[] newCharName = new char[newName.size()];
				for (int i = 0; i < newName.size(); i++) {
					newCharName[i] = newName.get(i);
				}
				name1 = String.copyValueOf(newCharName);
				String varp = "0x" + varpBit0;
				String end = "0x" + endValue;
				String line = "public static final Quest " + name1
						+ " = new Quest(" + q.getId() + ", " + varp + ", "
						+ end + ");";
				file.appendln(line);
			} catch (Exception e) {
				System.out.println("problem with " + name1);
				e.printStackTrace();
				break;
			}
		}
		if(!quests0.isEmpty())
		saveForScripts(file, quests0, scriptLoader, true);
		file.close();
	}

	@SuppressWarnings("unused")
	private int findCompleteValue(final Script script) {
		return 0;
	}

}
