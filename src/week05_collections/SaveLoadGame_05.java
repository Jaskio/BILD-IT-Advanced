package week05_collections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveLoadGame_05 {
	File file = new File("d:\\saveGame.txt");
	StringBuffer string = new StringBuffer();

	
	/** Save score */
	void submitScore(String name, int score) {
		extractFromFile(name);
		string.append(name + "," + score + System.lineSeparator());
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(string.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		string.delete(0, string.length());
	}
	
	/** Extract content from file */
	void extractFromFile(String name) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = reader.readLine()) != null) {
				if (!s.contains(name)) {
					string.append(s + System.lineSeparator());
				}
			}
		} catch (IOException e) {
			return;
		}
	}
	
	/** Find score in file */
	int loadScore(String name) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = reader.readLine()) != null) {
				String[] name_score = s.split(",");
				if (name_score[0].equals(name)) {
					return Integer.parseInt(name_score[1]);
				}
			}
		} catch (IOException e) {
			return 0;
		}
		return 0;
	}
}