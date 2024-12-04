package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.storelib.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AllDetailsController extends Functions implements Initializable{

	@FXML
	private ChoiceBox<String> viewAdminItem;

	@FXML
	private TableView<Product> adminView;

	@FXML
	private Label exp,saleOn;

	@FXML
	private TextField percentage;

	@FXML
	private AnchorPane anchor;

	Spinner<Integer> date = new Spinner<>();

	String[] categories = {"FOODS","CLOTHES","ELECTRONICS","ALL"};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		viewAdminItem.getItems().addAll(categories);
		viewAdminItem.setValue("ALL");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		onSaleColumn.setCellValueFactory(new PropertyValueFactory<>("onSale"));
		salePercentColumn.setCellValueFactory(new PropertyValueFactory<>("salePercent"));
		mfgDateColumn.setCellValueFactory(new PropertyValueFactory<>("mfgDate"));
		expDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
		manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
		electCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("electCategory"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
		subCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

		adminView.setItems(list);
		adminView.getColumns().addAll(categoryColumn, idColumn, nameColumn, priceColumn, onSaleColumn, salePercentColumn, quantityColumn);
		adminView.setPlaceholder(new Label("No Available Item For This Category"));

		adminView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				p=adminView.getSelectionModel().getSelectedItem();
				if(p.isOnSale()) saleOn.setText("Product On Sale");
				else saleOn.setText(null);
			}
		});

		saleOn.setText(null);

		viewAdminItem.setOnAction(this::viewAdminItems);
	}

	public void viewAdminItems(ActionEvent event){
		anchor.getChildren().remove(date);
		exp.setText(null);
		saleOn.setText(null);

		switch (viewAdminItem.getValue()) {
			case "FOODS": {
				adminView.getColumns().clear();
				ObservableList<Product> list = FXCollections.observableArrayList(Main.store.viewProductsAsAdmin(Category.FOOD));

				adminView.setItems(list);
				adminView.getColumns().addAll(idColumn, nameColumn, priceColumn, salePercentColumn, mfgDateColumn, expDateColumn, quantityColumn);

				//spinner for sale date
				date.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100, 3));
				AnchorPane.setRightAnchor(date, 260.0);
				AnchorPane.setBottomAnchor(date, 15.0);
				date.setPrefSize(100,26);
				anchor.getChildren().add(date);

				exp.setText("Foods Exp. in:");
				break;
			}
			case "CLOTHES": {
				adminView.getColumns().clear();
				ObservableList<Product> list = FXCollections.observableArrayList(Main.store.viewProductsAsAdmin(Category.CLOTHE));

				adminView.setItems(list);
				adminView.getColumns().addAll(idColumn, nameColumn, priceColumn, salePercentColumn, brandColumn, subCategoryColumn, sizeColumn, quantityColumn);
				break;
			}
			case "ELECTRONICS": {
				adminView.getColumns().clear();
				ObservableList<Product> list = FXCollections.observableArrayList(Main.store.viewProductsAsAdmin(Category.ELECTRONIC));

				adminView.setItems(list);
				adminView.getColumns().addAll(idColumn, nameColumn, priceColumn, salePercentColumn, manufacturerColumn, electCategoryColumn, quantityColumn);
				break;
			}
			case "ALL": {
				adminView.getColumns().clear();

				adminView.setItems(list);
				adminView.getColumns().addAll(categoryColumn, idColumn, nameColumn, priceColumn, onSaleColumn, salePercentColumn, quantityColumn);
				break;
			}
		}
	}

	public void giveSale(ActionEvent event){
		try {
			int per = Integer.parseInt(percentage.getText());
			percentage.setText(null);
			if (viewAdminItem.getValue().equals("FOODS")) {
				//all the item on given exp date needs to be onSale
				int d = date.getValue();
				Main.store.putOnSaleFood(d, per);
			} else {
				Main.store.putOnSaleAll(p.getId(), per);
			}

			//to see the changes in realtime
			adminView.refresh();

			DataHandler.saveData(Main.store);
		} catch (NumberFormatException e){
			alertBox("USE DIGITS FOR PERCENTAGE");
		}
	}
	
	public void admin(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/Admin.fxml");
	}

}
