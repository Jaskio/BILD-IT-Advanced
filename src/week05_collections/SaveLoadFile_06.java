package week05_collections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class SaveLoadFile_06 {
	String listener;
	String observableString;
	String filePath = "d:\\blacklist.dat";
	Set<String> set = new HashSet<>();
	
	
	Set<String> getSet() {
		return set;
	}
	
	String getIntoObservable() {
		return observableString;
	}
	
	String getListener() {
		return listener;
	}
	
	void setListener(String listener) {
		this.listener = listener;
	}
	
	void deleteFromList() {
		set.remove(listener);
	}
	
	void deleteAllFromList() {
		set.removeAll(set);
	}
	
	void setInput(String name, String jmbg, String address) {
		observableString = String.format("%s\t\t%s\t\t%s", name, jmbg, address);
		set.add(observableString);
	}
	
	/** Write set into file */
	void saveIntoFile() {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (String s : set) {
				writer.write(s);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Read from file */
	void extractFromFile() {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				set.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}