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
	Path filepath = Paths.get("loggingFile.txt");
	//gets path to the file that was created in the method fileCreate()
	String filestring = filepath.toString();
	//converts the path to a string variable so it can be referenced by the BufferedWriter and FileWriter
	public void fileCreate() {
		try {
			PrintWriter writer = new PrintWriter("loggingFile.txt", "UTF-8");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(filestring, true));
				bw.newLine();
				bw.write("******\nLogging only contains errors because it is redundant to log methods are functioning \nwhen no error being logged is the same as writing that it is working\n******");
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Creates a file using the PrintWriter class
			writer.close();
			//closes the file to prevent resource leak
		} 
		catch (FileNotFoundException e) {

		} 
		catch (UnsupportedEncodingException e) {

		}
	}

	public void fileLog(String c, String m, String s) {
		try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(filestring, true));
			bw.newLine();
			bw.write(c + m + s);
			bw.close();
			//writes message to the text file and closes it
		} 
		catch (IOException e) {
			
		}

	}
}