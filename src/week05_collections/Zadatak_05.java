package week05_collections;

	/*
	 * @author Jasmin Bektic
	 * 
	 * Napisati kviz program koji uèitava države i njihove glavne gradove iz filea po pokretanju ( lista ) te, surprise surprise, 
	 * ispisuje korisniku random pitanje da pogodi ili glavni grad ili državu sa date liste. Korisnik ima opciju, po pokretanju 
	 * programa, da unese broj random pitanja koje želi da ga program pita.
	 * 
	 * Bonus: Korisnik može unijeti svoje ime po pokretanju programa. Program po završetku igranja sprema u file ime korisnika 
	 * i broj tacnih odgovora. Po ponovnom pokretanju, ukoliko se isti korisnik “uloguje”, program ispisuje prethodni rezultat.
	 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Zadatak_05 extends Application {
	Stage window;
	TextField playerName = new TextField();
	TextField numberOfQuestions = new TextField();
	TextField questionAnswer = new TextField();	
	Label ingameInfo = new Label();
	CountriesAndCapitals_05 countriesAndCapitals = new CountriesAndCapitals_05();
	SaveLoadGame_05 saveLoad = new SaveLoadGame_05();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		window.setTitle("Quiz game");
		
		menu();
	}
	
	/** Main menu */
	void menu() {
		
		//TextField properties
		playerName.setPrefSize(100, 15);
		numberOfQuestions.setPrefSize(100, 15);
		
		//Entry message properties
		Label title = new Label("LET'S PLAY!");
		title.setTextFill(Color.rgb(45, 45, 45));
		title.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		
		//Create GridPane and set properties
		GridPane grid = new GridPane();
		grid.addRow(0, new Label("Your name:"), playerName);
		grid.addRow(1, new Label("Number of questions:"), numberOfQuestions);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setAlignment(Pos.CENTER);
		
		//Play button
		Button play = new Button("Play");
		play.setPrefSize(70, 15);
		play.setOnAction(e -> {
			ingameInfo.setText("Player: " + playerName.getText() + "  |  Previous score: " + saveLoad.loadScore(playerName.getText()));
			countriesAndCapitals.setQuestionNumbers(Integer.parseInt(numberOfQuestions.getText()));
			countriesAndCapitals.setMapQuestions();
			gameQuiz();
		});
		
		//Import nodes
		VBox vbox = new VBox(title, grid, play);
		vbox.setSpacing(15);
		vbox.setAlignment(Pos.CENTER);
		
		//Set scene
		window.setScene(new Scene(vbox, 300, 170));
		window.centerOnScreen();
		window.show();
	}
	
	/** Game area */
	void gameQuiz() {
		BorderPane pane = new BorderPane(ingameInfo);
		BorderPane.setAlignment(ingameInfo, Pos.TOP_LEFT);
		
		Label question = new Label();
		question.setTextFill(Color.BLUE);
		question.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		question.setText(countriesAndCapitals.getQuestion());
		
		HBox hbox = new HBox(new Label("Answer:"), questionAnswer);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		
		Label text = new Label();

		//Button properties and condition for correct answers
		Button ok = new Button("OK");
		ok.setPrefSize(70, 15);
		ok.setOnAction(e -> {
			if (questionAnswer.getText().toLowerCase().equals(countriesAndCapitals.getAnswer().toLowerCase())) {
				text.setText("Correct!!");
				countriesAndCapitals.setAnswerCounter();
			} else {
				text.setText("meh, wrong! Correct answer was " + countriesAndCapitals.getAnswer());
			}
			countriesAndCapitals.removeFromMap();
			if (countriesAndCapitals.getMapSize() == 0) {
				submitResult();
			} else {
			question.setText(countriesAndCapitals.getQuestion());
			questionAnswer.clear();
			}
		});
		
		//Set VBox properties
		VBox vbox = new VBox(pane, question, hbox, text, ok);
		vbox.setSpacing(23);
		vbox.setAlignment(Pos.CENTER);
		
		//Set scene
		window.setScene(new Scene(vbox, 300, 200));
		window.centerOnScreen();
		window.show();
	}
	
	/** Pop-up window for submiting score */
	void submitResult() {
		Stage newStage = new Stage();
	
		//Button properties
		Button submit = new Button("Submit and back to menu");
		VBox vbox = new VBox(new Label("You had " + countriesAndCapitals.getAnswerCounter() + " correct answers!"), submit);
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);
		submit.setOnAction(e -> {
			saveLoad.submitScore(playerName.getText(), countriesAndCapitals.getAnswerCounter());
			playerName.clear();
			numberOfQuestions.clear();
			menu();
			newStage.close();
		});
		
		//Set scene with new stage
		newStage.setScene(new Scene(vbox, 250, 100));
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.centerOnScreen();
		newStage.showAndWait();
	}
	
	public static void main(String[] args) {
		launch();
	}
}