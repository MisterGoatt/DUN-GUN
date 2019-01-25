package BackEnd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import collisions.CollisionDetector;
import levels.Level1;
import levels.Level3;
import screens.MainMenu;
import screens.levelCompleted;

public class PointSystem {

	public static int sum;// create an integer that can be used by any class
	public static int x;
	public static int y;
	public static int z = 0;
	public static void pointFile() {

			try {
				y = sum;
				BufferedWriter hs;
				BufferedWriter bw;
				BufferedReader reader;

				if (!MainMenu.pointStart) { // checks to see if pointStart is false
					bw = new BufferedWriter(new FileWriter("playerScore", true));// Sets the file so that all information is kept in the file

					// Adds points to the file for each death of an enemy, according to there point values. It is accomplished by checking
					if (CollisionDetector.soldierDeath) {
						bw.write(String.valueOf(100));
						sum += 100;
						bw.newLine();// Starts the new line on the file
						bw.close();// saves the information written to the file

						CollisionDetector.soldierDeath = false; // set's the boolean back to false
					} else if (CollisionDetector.scientistDeath) {
						bw.write(String.valueOf(75));
						sum += 75;
						bw.newLine();// Starts the new line on the files
						bw.close();// saves the information written to the file

						CollisionDetector.scientistDeath = false; // set's the boolean back to false
					} else if (CollisionDetector.gruntDeath) {
						bw.write(String.valueOf(40));
						sum += 40;
						bw.newLine();// Starts the new line on the file
						bw.close();// saves the information written to the file

						CollisionDetector.gruntDeath = false; // set's the boolean back to false
					} else if (CollisionDetector.flayerDeath) {
						bw.write(String.valueOf(150));
						sum += 150;
						bw.newLine();// Starts the new line on the file
						bw.close();// saves the information written to the file

						CollisionDetector.flayerDeath = false; // set's the boolean back to false
					} else if (CollisionDetector.turretDeath) {
						bw.write(String.valueOf(175));
						sum += 175;
						bw.newLine();// Starts the new line on the file
						bw.close();// saves the information written to the file

						CollisionDetector.turretDeath = false; // set's the boolean back to false
					} else if (CollisionDetector.ivanovDeath) {
						bw.write("double");
						sum *= 2;
						bw.newLine();// Starts the new line on the file
						bw.close();// saves the information written to the file

						CollisionDetector.ivanovDeath = false; // set's the boolean back to false
					} else if (Level3.pointCountGame) {

						y = sum;
						try {
							if (y > x)                       // and keep track of the largest
							{ // if the current score is greater than the high score, a new high score will be printed the highScore screen and saved as variable x.
								x = y; 
								hs = new BufferedWriter(new FileWriter("highScore", false));
								hs.write("");
								hs.close();
								hs = new BufferedWriter(new FileWriter("highScore", true));
								hs.write(String.valueOf(x));
								hs.close();
							}
						} catch (NumberFormatException e1) {
							// ignore invalid scores
							//System.err.println("ignoring invalid score: " + line);
						}	

						Level3.pointCountGame = false; // set's the boolean back to false
					}else if (MainMenu.pointsMenu) {
						sum = 0;
						MainMenu.pointStart = false; // set's the boolean back to false
						MainMenu.pointsMenu = false; // set's the boolean back to false
					}
				} else if (MainMenu.pointStart) {// checks to see if pointStart is true
					File file = new File("playerScore");
					File file2 = new File("highScore");
					file.createNewFile();// creates a new file if the file does not already exist
					file2.createNewFile();
					bw = new BufferedWriter(new FileWriter("playerScore", false));// Sets the file so that all information is not kept in the file
					bw.write("");// writes blank space into the file effectively clearing the file
					bw.close();
					int sum = 0; // resets the score to zero
					if (z == 0) {
						hs = new BufferedWriter(new FileWriter("highScore", false));// clears the file highScore
						hs.write("");
						hs.close();
						hs = new BufferedWriter(new FileWriter("highScore", true));
						hs.write(String.valueOf(x));
						hs.close();
						z++;
					}
					reader = new BufferedReader(new FileReader("highScore"));
					String z = reader.readLine();
					x = Integer.parseInt(z);
					MainMenu.pointStart = false;// sets pointStart to false
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	

}
