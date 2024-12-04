package application;

import java.io.IOException;
import java.time.LocalDate;

import application.storelib.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Functions {
	Product p;

	ObservableList<Product> list = FXCollections.observableArrayList(Main.store.getProducts());
	ObservableList<Product> cartList = FXCollections.observableArrayList(Main.store.getCart().getItems());

	TableColumn<Product, String> idColumn = new TableColumn<>("ID");
	TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
	TableColumn<Product, Category> categoryColumn = new TableColumn<>("Category");
	TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
	TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
	TableColumn<Product, Boolean> onSaleColumn = new TableColumn<>("OnSale");
	TableColumn<Product, Double> salePercentColumn = new TableColumn<>("SalePercent");
	TableColumn<Product, LocalDate> mfgDateColumn = new TableColumn<>("MFG Date");
	TableColumn<Product, LocalDate> expDateColumn = new TableColumn<>("EXP Date");
	TableColumn<Product, String> manufacturerColumn = new TableColumn<>("Manufacturer");
	TableColumn<Product, ElectCategory> electCategoryColumn = new TableColumn<>("ElectCategory");
	TableColumn<Product, String> brandColumn = new TableColumn<>("Brand");
	TableColumn<Product, SubCategory> subCategoryColumn = new TableColumn<>("SubCategory");
	TableColumn<Product, Size> sizeColumn = new TableColumn<>("Size");
	TableColumn<Product, Double> saleColumn = new TableColumn<>("SalePrice");

	public void sceneSwitch(ActionEvent event,String fxml) throws IOException {
		Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
		s.hide();
		
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(root);
		primaryStage.setTitle("MewMarket");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void alertBox(String message){
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("WARNING!!");
		window.setMinWidth(350);
		window.setMinHeight(150);

		Label label = new Label();
		label.setText(message);
		label.setStyle("-fx-text-fill: #ed2939");
		label.setFont(new Font("Lancer",20));

		Button closeButton = new Button("OKAY");
		closeButton.setStyle("-fx-background-color: #54fffb");
		closeButton.setOnAction(e -> window.close());

		AnchorPane root = new AnchorPane();
		root.setStyle("-fx-background-color: #00c6cc");

		AnchorPane.setRightAnchor(closeButton,15.0);
		AnchorPane.setBottomAnchor(closeButton,15.0);

		AnchorPane.setBottomAnchor(label,60.0);
		AnchorPane.setRightAnchor(label, 15.0);
		AnchorPane.setLeftAnchor(label, 15.0);
		AnchorPane.setTopAnchor(label, 15.0);

		root.getChildren().addAll(label,closeButton);

		Scene scene = new Scene(root);
		window.setScene(scene);
		window.showAndWait();
	}
}
