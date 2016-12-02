package week05_collections;

	/*
	 * @author Jasmin Bektic
	 * 
	 * Napisati program koji uèitava rijeèi iz tekst filea i ispisuje sve rijeci unutar tog filea:
	 * a. po abecednom redu. (duplikati dozvoljeni)
	 * b. po abecednom redu. (duplikati nisu dozvoljeni)
	 * 
	 * Da bi rijeèi bile ispisane, moraju poèeti slovom.
	 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Zadatak_01 {
	static Set<String> nonDuplicates = new TreeSet<>();
	static ArrayList<String> duplicates = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException, IOException {
		File file = new File("d:\\zadatak01.txt");
		printDuplicates(file);
		System.out.println();
		printNonDuplicates(file);
	}
	
	/* Method prints ordered list with all words */
	static void printDuplicates(File file) throws IOException {
		
		//Read lines from file
		try (BufferedReader line = new BufferedReader(new FileReader(file))) {
			String s;
			while((s = line.readLine()) != null) {
					Collections.addAll(duplicates, s.split(" "));
			} 
		}
		//Sort collection and print words
		Collections.sort(duplicates);
		for (String s : duplicates) {
			if (!Character.isDigit(s.charAt(0))) {
				System.out.println(s);
			}
		}
	}
	
	/* Method prints list without duplicates */
	static void printNonDuplicates(File file) throws IOException {
		
		//Read lines from file
		try (BufferedReader line = new BufferedReader(new FileReader(file))) {
			String s;
			while((s = line.readLine()) != null) {
					Collections.addAll(nonDuplicates, s.split(" "));
			}
		}
		//Printing words
		for (String s : nonDuplicates) {
			if (!Character.isDigit(s.charAt(0))) {
				System.out.println(s);
			}
		}
	}
}