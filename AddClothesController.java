package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import application.storelib.Size;
import application.storelib.SubCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class AddClothesController extends Functions implements Initializable{

	@FXML
	private TextField clotheName,clotheID,clothePrice,clotheBrand;
	
	@FXML
	private ChoiceBox<SubCategory> subCategory;
	
	@FXML
	private ChoiceBox<Size> size;

	@FXML
	private Spinner<Integer> clotheQuantity;

	@FXML
	private Label done;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		subCategory.getItems().addAll(SubCategory.values());
		size.getItems().addAll(Size.values());

		SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000,1);

		clotheQuantity.setValueFactory(value);
	}

	public void enter(ActionEvent event) {
		try {
			String name = clotheName.getText();
			String id = clotheID.getText();
			String brand = clotheBrand.getText();
			SubCategory sCat = subCategory.getValue();
			Size csize = size.getValue();
			int quantity = clotheQuantity.getValue();
			double price = Double.parseDouble(clothePrice.getText());
			if(name.isBlank() || id.isBlank() || brand.isBlank() || sCat==null || csize==null){
				throw new NullPointerException();
			}
			Main.store.addProduct(name, id, quantity, brand, sCat, csize, price);
			DataHandler.saveData(Main.store);
			done.setText("ADDED");

			clotheName.setText(null);
			clotheID.setText(null);
			clothePrice.setText(null);
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
