package week05_collections;

	/*
	 * @author Jasmin Bektic
	 * 
	 * Napisati program koji služi kao mini adresar - program pita korisnika da unese ime i prezime + broj telefona kontakta/email adresu. 
	 * Program daje korisniku moguænost da: 
	 * a. Unese novi kontakt
	 * b. Izlista sve pohranjene kontakte po abecednom redu
	 */

import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Zadatak_04 extends Application {
	Button newContact = new Button("Add New Contact");
	Button listContacts = new Button("List All Contacts");
	Button add = new Button("Add");
	Button back = new Button("<- Back To Menu");
	TextField contactName = new TextField();
	TextField contactInformation = new TextField();
	Map<String, String> map = new TreeMap<>();
	StringBuffer textString = new StringBuffer();
	
	
	public String getName() {
		return contactName.getText();
	}
	
	public String getInformation() {
		return contactInformation.getText();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		//Set actions
		newContact.setOnAction(e -> stage.setScene(addContact()));
		listContacts.setOnAction(e -> {
			exportFromMap();
			stage.setScene(contactList());
		});
		back.setOnAction(e -> stage.setScene(menu()));
		add.setOnAction(e -> {
			addToMap();
			contactName.clear();
			contactInformation.clear();
		});
		
		stage.setScene(menu());
		stage.setTitle("Adress book");
		stage.show();
	}
	
	/** Menu scene */
	Scene menu() {
		
		//Set button properties
		newContact.setPadding(new Insets(10, 30, 10, 30));
		listContacts.setPadding(new Insets(10, 35, 10, 35));
		
		//Create pane and import nodes
		VBox vbox = new VBox(newContact, listContacts);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		
		return new Scene(vbox, 300, 150);
	}
	
	/** Contact form */
	Scene addContact() {
		back.setAlignment(Pos.BOTTOM_LEFT);
		
		//Create pane and set properties
		GridPane pane = new GridPane();
		pane.addRow(0, new Label("Enter name:"), contactName);
		pane.addRow(1, new Label("Enter phone number/e-mail:"), contactInformation);
		pane.add(add, 1, 2);
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setAlignment(Pos.CENTER);
		pane.setPrefHeight(120);
		pane.setPrefWidth(350);
		
		return new Scene(new VBox(pane, back));
	}
	
	/** Contact listing */
	Scene contactList() {
		HBox hbox = new HBox(new Label("Name"), new Label("Information"));
		hbox.setSpacing(100);
		
		ScrollPane scrollpane = new ScrollPane(new Text(textString.toString()));
		scrollpane.setPrefHeight(110);
		
		VBox vbox = new VBox(hbox, scrollpane, back);
		vbox.setPrefWidth(300);
		
		return new Scene(vbox);
	}
	
	/** Import into map */
	public void addToMap() {
		map.put(getName(), getInformation());
	}
	
	/** Export from map */
	public void exportFromMap() {
		textString.delete(0, textString.length());
		for (Map.Entry<String, String> m : map.entrySet()) {
			textString.append(m.getKey() + "\t\t\t" + m.getValue() + "\n");
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
}