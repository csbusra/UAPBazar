package application;

import application.storelib.Cart;
import application.storelib.Store;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class Main extends Application {
	public static Store store;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("design/MainMenu.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("MewMarket");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		store = DataHandler.loadData();
		launch(args);
	}
}
