import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TextOutput {
	
	String textFileLocation;
	PrintWriter writeFile;
	
	public TextOutput(String textFile) throws FileNotFoundException {
		this.textFileLocation = textFile;
		writeFile = new PrintWriter(textFileLocation);
	}
	
	
	public void writeNewSingle(String content) throws FileNotFoundException {
		writeFile = new PrintWriter(textFileLocation);
		writeFile.println(content);
		writeFile.close();
	}
	
	public void writeNewSingle(int content) throws FileNotFoundException {
		writeFile = new PrintWriter(textFileLocation);
		writeFile.println(content);
		writeFile.close();
	}
	
}
