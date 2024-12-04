package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MainController extends Functions {
	
	public void admin(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/Admin.fxml");
	}

	public void customer(ActionEvent event) throws IOException {
		sceneSwitch(event, "design/Customer.fxml");
	}
	
	public void closeButton(ActionEvent event){
	    final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
}
