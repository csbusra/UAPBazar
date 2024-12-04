package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class AdminController extends Functions implements Initializable{
	
	@FXML
	private ChoiceBox<String> addToStore;
	
	String[] category = {"FOODS","CLOTHES","ELECTRONICS"};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addToStore.getItems().addAll(category);
		addToStore.setValue("FOODS");
	}
	
	public void addItems(ActionEvent event) throws IOException {
		switch (addToStore.getValue()) {
			case "FOODS":
				sceneSwitch(event, "design/AddFoods.fxml");
				break;
			case "CLOTHES":
				sceneSwitch(event, "design/AddClothes.fxml");
				break;
			case "ELECTRONICS":
				sceneSwitch(event, "design/AddElectronics.fxml");
				break;
		}
	}
	public void details(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/AllDetails.fxml");
	}
	public void mainMenu(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/MainMenu.fxml");
	}
}