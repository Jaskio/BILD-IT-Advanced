package week05_collections;

	/*
	 * @author Jasmin Bektic
	 * 
	 * Napisati program koji pohranjuje 5 miliona cijelih brojeva u LinkedListu i ArrayListu te ispisuje vrijeme potrebno 
	 * da se prodje kroz obje kolekcije koristeæi se iteratorom vs. get(index) metodom.
	 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Zadatak_02 {

	public static void main(String[] args) {
		List<Integer> linked = new LinkedList<>();
		List<Integer> list = new ArrayList<>();
		
		//Import 5 million integers
		for (int i = 0; i < 5_000_000; i++) {
			linked.add(i);
			list.add(i);
		}
		System.out.println("Numbers imported!");
		
		//Measure iterate time on LinkedList
		long start = System.currentTimeMillis();
		iterate(linked);
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) + "ms");
		
		//Measure iterate time on ArrayList
		start = System.currentTimeMillis(); 
		iterate(list);
		end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) + "ms");
		
		//Measure time with get() method on LinkedList
		start = System.currentTimeMillis();
		getNumber(linked);
		end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) + "ms");
		
		//Measure time with get() method on ArrayList
		start = System.currentTimeMillis();
		getNumber(list);
		end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) + "ms");
	}
	
	static void iterate(List<Integer> list) {
		while(!list.iterator().hasNext()) {
			list.iterator().next();
		}
	}
	
	static void getNumber(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i);
		}
	}
}