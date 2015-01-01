import java.util.ArrayList;
import java.util.HashMap;

import com.sk.cache.wrappers.QuestDefinition;
import com.sk.cache.wrappers.loaders.QuestDefinitionLoader;

public class QuestDump {

	public QuestDump(final QuestDefinitionLoader loader, final boolean forScript) {
		ArrayList<QuestDefinition> quests = new ArrayList<QuestDefinition>();
		int i = 0;
		while (loader.canLoad(i)) {
			quests.add(loader.load(i));
			i++;
		}
		System.out.println(quests.size() +" quests loaded");
		FileSaver file = new FileSaver("QuestDump.txt",
				"C:\\users\\Robert\\Desktop\\");
		if (forScript)
			saveForScripts(file, quests);
	}

	private void saveForScripts(FileSaver file,
			ArrayList<QuestDefinition> quests) {
		String name1 = "";
		for(QuestDefinition q:quests){
			System.out.println(q);
			try {
				String name0 = q.name;
				int varpBit0 = q.configId[0];
				//System.out.println(varpBit0);
				int endValue = q.configEndValue[0];
				name0.toUpperCase();
				String[] nameStuff = name0.split(" ");
				 name1 =name0.replace(" ", "_");
				String varp = "0x"+varpBit0;
//				for(int i:varpBit0)
//					varp= varp+i;
				String end = "0x"+endValue;
				
				String line = "public static final Quest "+name1+" = new Quest("+q.getId()+", "+varp+", "+end+");";
				file.appendln(line);
			} catch (Exception e) {
				System.out.println("problem with " +name1);
				e.printStackTrace();
				break;
			}
		}
		file.close();
	}

}
