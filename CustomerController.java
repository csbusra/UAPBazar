package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import application.storelib.Category;
import application.storelib.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerController extends Functions implements Initializable{

	@FXML
	private ChoiceBox<Category> viewCustomerItem;

	@FXML
	private TableView<Product> customerView;

	@FXML
	private Spinner<Integer> customerQuantity;

	SpinnerValueFactory<Integer> value;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		viewCustomerItem.getItems().addAll(Category.values());
		customerView.setPlaceholder(new Label("Select A Category"));

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		saleColumn.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		customerView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				p = customerView.getSelectionModel().getSelectedItem();
				value = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, p.getQuantity(),1);

				customerQuantity.setValueFactory(value);
			}
		});

		viewCustomerItem.setOnAction(this::viewCustomerItems);
	}

	public void viewCustomerItems(ActionEvent event){
		customerView.getColumns().clear();
		ObservableList<Product> list = FXCollections.observableArrayList(Main.store.viewProducts(viewCustomerItem.getValue()));

		customerView.setItems(list);
		customerView.getColumns().addAll(nameColumn, priceColumn, saleColumn, quantityColumn);
		customerView.setPlaceholder(new Label("No Available Item For This Category"));
	}
	
	public void cart(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/Cart.fxml");
	}

	public void addToCart(ActionEvent event){
		//if spinner value is 0, show Out of Stock
		try {
			String id = p.getId();
			int amt = customerQuantity.getValue();
			if(amt==0) throw new Exception();
			try {
				Main.store.addProductToCart(id, amt);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				//this will never occur
			}
			value.setValue(1);
			customerQuantity.setValueFactory(value);
		} catch (Exception e) {
			alertBox("OUT OF STOCK");
		}
	}
	
	public void mainMenu(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/MainMenu.fxml");
	}
}
