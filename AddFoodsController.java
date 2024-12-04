package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class AddFoodsController extends Functions implements Initializable{
	
	@FXML
	private TextField foodName,foodID,foodPrice;

	@FXML
	private DatePicker foodDate;

	@FXML
	private Spinner<Integer> foodQuantity;

	@FXML
	private Label done;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000,1);
		
		foodQuantity.setValueFactory(value);
	}
	
	public void enter(ActionEvent event){
		try{
			String name = foodName.getText();
			String id = foodID.getText();
			double price = Double.parseDouble(foodPrice.getText());
			int quantity = foodQuantity.getValue();
			LocalDate mfg = LocalDate.now();
			LocalDate exp = foodDate.getValue();
			if(name.isBlank() || id.isBlank() || exp==null){
				throw new NullPointerException();
			}
			Main.store.addProduct(name, id, quantity, mfg, exp, price);
			DataHandler.saveData(Main.store);
			done.setText("ADDED");

			foodName.setText(null);
			foodID.setText(null);
			foodPrice.setText(null);
		} catch (NullPointerException e){
			alertBox("ONE OR MORE FIELDS ARE EMPTY");
		} catch (NumberFormatException e){
			alertBox("USE DIGITS FOR PRICE");
		}
	}

	public void admin(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/Admin.fxml");
	}	
}
