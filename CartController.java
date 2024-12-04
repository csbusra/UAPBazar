package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import application.storelib.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CartController extends Functions implements Initializable{

	@FXML
	private TableView<Product> cartView;

	@FXML
	private Spinner<Integer> cartQuantity;

	@FXML
	private Label bill,order;

	int index;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bill.setText("TOTAL: "+Main.store.totalBill()+" TK");

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		saleColumn.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		cartView.setItems(cartList);
		cartView.getColumns().addAll(nameColumn, priceColumn, saleColumn, quantityColumn);
		cartView.setPlaceholder(new Label("Cart is Empty"));

		cartView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				p=cartView.getSelectionModel().getSelectedItem();
				index = cartView.getSelectionModel().getSelectedIndex();

				//to get product available quantity
				int max = Main.store.getProducts().get(Main.store.findProduct(p.getId())).getQuantity();
				SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,max, p.getQuantity());
				cartQuantity.setValueFactory(value);
			}
		});
	}

	public void payBill(ActionEvent event){
		double tk = Main.store.payBill();
		order.setText("PAYMENT:"+tk+" TK,CONFIRMED");
		bill.setText("TOTAL: "+Main.store.totalBill()+" TK");

		//to see realtime change
		cartView.getItems().clear();
	}

	public void removeItem(ActionEvent event){
		Main.store.removeProductFromCart(p.getId());
		bill.setText("TOTAL: "+Main.store.totalBill()+" TK");

		//to see realtime change
		cartView.getItems().remove(index);
	}

	public void updateCount(ActionEvent event){
		String id = p.getId();
		int count = cartQuantity.getValue();
		Main.store.updateProductInCart(id,count);
		bill.setText("TOTAL: "+Main.store.totalBill()+" TK");

		//to see realtime change
		cartView.refresh();
	}

	public void clearCart(ActionEvent event){
		Main.store.clearCart();
		bill.setText("TOTAL: "+Main.store.totalBill()+" TK");

		//to see realtime change
		cartView.getItems().clear();
	}
	
	public void customer(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/Customer.fxml");
	}
}
