package BackEnd;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogFileHandler{

	public void fileCreate() {

		try {
			PrintWriter writer = new PrintWriter("loggingFile.txt", "UTF-8");
			//Creates a file using the PrintWriter class
			writer.close();
			//closes the file to prevent resource leak
		} 
		catch (FileNotFoundException e) {

			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

	}

	public void fileLog(String c, String m, String s) {
		Path filepath = Paths.get("loggingFile.txt");
		//gets path to the file that was created in the method fileCreate()
		String filestring = filepath.toString();
		//converts the path to a string variable so it can be referenced by the BufferedWriter and FileWriter
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filestring, true));
			bw.newLine();
			bw.write(c + m + s);
			bw.close();
			//writes message to the text file and closes it
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}