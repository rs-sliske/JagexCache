import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSaver {

	private PrintWriter file;

	public FileSaver(String fileName) {
		String directory = "C:\\users\\Robert";
		directory += "\\models\\";
		try {
			//File f = new File(directory + fileName);
			//f.createNewFile();
			file = new PrintWriter(directory+fileName);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		if (file != null) {
			System.out.println("file " + fileName + " created");
		}

	}
	
	public void append(String s){
		file.append(s);
	}
	
	public void close(){
		file.close();
	
	}

}
