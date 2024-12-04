package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import application.storelib.ElectCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class AddElectronicsController extends Functions implements Initializable{

	@FXML
	private TextField electName,electID,electPrice,electManufacturer;

	@FXML
	private ChoiceBox<ElectCategory> subCategory;

	@FXML
	private Spinner<Integer> electQuantity;

	@FXML
	private Label done;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		subCategory.getItems().addAll(ElectCategory.values());

		SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000,1);

		electQuantity.setValueFactory(value);
	}

	public void enter(ActionEvent event) {
		try {
			String name = electName.getText();
			String id = electID.getText();
			String manufacturer = electManufacturer.getText();
			ElectCategory sCat = subCategory.getValue();
			int quantity = electQuantity.getValue();
			double price = Double.parseDouble(electPrice.getText());
			if(name.isBlank() || id.isBlank() || sCat==null || manufacturer==null){
				throw new NullPointerException();
			}
			Main.store.addProduct(name, id, quantity, manufacturer, sCat, price);
			DataHandler.saveData(Main.store);
			done.setText("ADDED");

			electName.setText(null);
			electID.setText(null);
			electPrice.setText(null);
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
