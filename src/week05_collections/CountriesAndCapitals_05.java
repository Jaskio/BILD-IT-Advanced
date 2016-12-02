package week05_collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CountriesAndCapitals_05 {
	private Map<String, String> randomQuestions = new HashMap<>();
	int correctAnswers;
	int questionNumbers;
	int answerCounter = 0;
	int randNum;
	
	
	String getQuestion() {
		randNum = (int) (Math.random() + 1);
		String s = "";
		switch(randNum) {
			case 0: s = "What is the capital city of " + randomQuestions.keySet().iterator().next() + "?"; break;
			case 1: s = randomQuestions.values().iterator().next() + " is capital city of?"; break;
		}
		return s;
	}
	
	String getAnswer() {
		String s = "";
		switch(randNum) {
			case 1: s = randomQuestions.keySet().iterator().next(); break;
			case 0: s = randomQuestions.values().iterator().next(); break;
		}
		return s;
	}
	
	int getAnswerCounter() {
		return answerCounter;
	}
	
	void setAnswerCounter() {
		answerCounter++;
	}
	
	int getMapSize() {
		return randomQuestions.size();
	}
	
	void setQuestionNumbers(int questionNumbers) {
		this.questionNumbers = questionNumbers;
	}
	
	void removeFromMap() {
		randomQuestions.remove(randomQuestions.keySet().iterator().next());
	}
	
	/** Import content from file into map */
	void setMapQuestions() {
		File file = new File("d:\\countriesAndCapitals.txt");
		
		Map<String, String> map = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String s;
			while((s = reader.readLine()) != null) {
				String[] words = s.split(",");
				map.put(words[0], words[1]);
			}
			preparedQuestions(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Create new map with prepared number of questions randomly shuffled */
	void preparedQuestions(Map<String, String> map) {
		List<String> keysFromMap = new ArrayList<>(map.keySet());
		List<String> valuesFromMap = new ArrayList<>(map.values());
		
		Random rand = new Random();
		for (int i = 0; i < questionNumbers; i++) {
			int randomNum = rand.nextInt(rand.nextInt(keysFromMap.size()));
			randomQuestions.put(keysFromMap.get(randomNum), valuesFromMap.get(randomNum));
		}
	}
}