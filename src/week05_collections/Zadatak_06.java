package week05_collections;

	/*
	 * @author Jasmin Bektic
	 * 
	 * Napisati implementaciju No Fly Liste u vidu konzolne aplikacije. Aplikacija treba da spremi sljedeće podatke u jadniku 
	 * koji se zadesi na listi:
	 * ● Ime i prezime
	 * ● JMBG
	 * ● Adresu stanovanja (ulica, grad, zip code)
	 * 
	 * Aplikacija treba da bude persistent - jednom kada ju pokrenete i unesete nesretnika, sljedeći put kada pokrenete 
	 * aplikaciju - nesretnik je još uvijek na listi. Nema zajebancije sa našom listom, sistem ne zaboravlja. Korisnik treba 
	 * da ima mogućnost, ukoliko se smiluje i ukoliko mu to odgovara, da ukloni paćenika sa liste. Korisnik također treba da 
	 * ima mogućnost da izlista sve sretne dobitnike u za oko finom toStringu. Korisnik treba imati mogućnost da pretraži 
	 * listu na osnovu kriterija po vašoj želji - vi ste ipak ti koji, svojim prstima, smanjujete entropiju u zatvorenom 
	 * sistemu. Mentalni divovi. Ukoilko korisnika počne da grize savjet, smara nesanica ili se zabrine za spas svoje duše 
	 * besmrtne - korisnik treba imati opciju da ukloni sve nesretnike s liste jednim potezom. Pretpostavimo da je korisnik 
	 * sretan nakon ovoga i da živi bezbrižno do kraja svojih dana.
	 */

import java.util.TreeSet;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Zadatak_06 extends Application {
	Stage window;
	TextField name = new TextField();
	TextField jmbg = new TextField();
	TextField address = new TextField();
	SaveLoadFile_06 database = new SaveLoadFile_06();

	
	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		database.extractFromFile();
		main();
	}
	
	void main() {
		
		//Create grid and set properties
		GridPane grid = new GridPane();
		grid.addRow(0, new Label("Name:"), name);
		grid.addRow(1, new Label("JMBG:"), jmbg);
		grid.addRow(2, new Label("Address:"), address);
		grid.setHgap(10);
		grid.setVgap(5);
		grid.setAlignment(Pos.CENTER);
		
		Button enter = new Button("Enter");
		enter.setPrefSize(70, 15);
		
		//Application left side properties
		VBox leftSide = new VBox(grid, enter);
		leftSide.setSpacing(20);
		leftSide.setAlignment(Pos.CENTER);
		
		Label blacklist = new Label("BLACKLIST");
		blacklist.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		
		//Headlines
		Label nameHeadline = new Label("Name");
		nameHeadline.setStyle("-fx-font-weight: bold");
		Label jmbgHeadline = new Label("JMBG");
		jmbgHeadline.setStyle("-fx-font-weight: bold");
		Label addressHeadline = new Label("Address");
		addressHeadline.setStyle("-fx-font-weight: bold");
		
		HBox headline = new HBox(nameHeadline, jmbgHeadline, addressHeadline);
		headline.setSpacing(100);
		
		//Updates for observable list
		ObservableList<String> olist = FXCollections.observableArrayList(new TreeSet<>(database.getSet()));
		ListView<String> lv = new ListView<>(olist);
		lv.setPrefSize(350, 200);
		lv.setStyle("-fx-background-color: ghostwhite");		
		lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lv.getSelectionModel().selectedItemProperty().addListener(e -> {
			database.setListener(lv.getSelectionModel().getSelectedItem());
		});
		
		//Buttons properties
		Button delete = new Button("Delete");
		delete.setPrefSize(70, 15);
		delete.setOnAction(e -> {
			database.deleteFromList();
			olist.remove(database.getListener());
		});
		Button deleteAll = new Button("Delete All");
		deleteAll.setPrefSize(70, 15);
		deleteAll.setOnAction(e -> {
			database.deleteAllFromList();
			olist.clear();
		});
		enter.setOnAction(e -> {
			database.setInput(name.getText(), jmbg.getText(), address.getText());
			olist.add(database.getIntoObservable());
			name.clear();
			jmbg.clear();
			address.clear();
		});
				
		HBox btnHorizontal = new HBox(delete, deleteAll);
		btnHorizontal.setSpacing(10);
		btnHorizontal.setAlignment(Pos.CENTER);

		//Application right side properties
		VBox rightSide = new VBox(blacklist, headline, new ScrollPane(lv), btnHorizontal);
		rightSide.setSpacing(10);
		rightSide.setAlignment(Pos.CENTER);
		
		HBox hbox = new HBox(leftSide, rightSide);
		hbox.setSpacing(20);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		
		window.setScene(new Scene(hbox));
		window.setOnCloseRequest(e -> database.saveIntoFile());
		window.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}