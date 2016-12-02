package week05_collections;

	/*
	 * @author Jasmin Bektic
	 * 
	 * Napisati program koji prima dva argumenta - ime txt filea, te broj linija teksta za isprintati. Program ucitava sadrzaj 
	 * tekstualnog filea u listu te ispisuje random linije teksta iz filea. Broj linija koje program ispisuje je jednak 
	 * drugom unesenom argumentu. Napisati program tako da lista bude correctly-sized od samog starta, ne da raste kako 
	 * se file ucitava.
	 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;

public class Zadatak_03 {
	static int numberOfLines;

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			throw new InvalidParameterException("Usage: java Zadatak_03 filename.txt numberOfLines");
		}
		File file = new File("d:\\" + args[0]);
		
		if (!file.exists()) {
			throw new FileNotFoundException("File not found");
		}
		
		numberOfLines = Integer.parseInt(args[1]);
		List<String> list = Arrays.asList(new String[setSize(file)]);
		
		extractFromFile(list, file);
		Collections.shuffle(list);
		printRandomLines(list);
	}
	
	/** Determine correct Set capacity by number of lines in file 
	 * @throws IOException*/
	static int setSize(File file) throws IOException {
		int count = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (reader.readLine() != null) {
				count++;
			}
		} 
		return count;
	}
	
	/** Get content from file 
	 * @throws IOException */
	static void extractFromFile(List<String> list, File file) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String s;
			int count = 0;
			while ((s = reader.readLine()) != null) {
				list.set(count++, s);
			}
		} 
	}
	
	/** Print random lines */
	static void printRandomLines(List<String> list) {
		for (String s : list) {
			if (numberOfLines == 0) {
				break;
			}
			System.out.println(s);
			numberOfLines--;
		}
	}
}