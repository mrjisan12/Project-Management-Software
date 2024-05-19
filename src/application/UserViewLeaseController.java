package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class UserViewLeaseController {
	
	

	@FXML
	public void UserViewAppartment(ActionEvent event) throws IOException {
		loadScene("UserViewAppart.fxml");
	}
	
	
	@FXML
	public void UserViewCommercial(ActionEvent event) throws IOException {
		loadScene("UserViewCom.fxml");
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		loadScene("LoginPage.fxml");
	}
	
	private void loadScene(String fxml) throws IOException {
		Pane root = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(root);
		Main.primaryStage.setScene(scene);
		Main.primaryStage.setTitle("Property Management Software");
		Image icon = new Image("/images/icon.png");
		Main.primaryStage.getIcons().add(icon);
		Main.primaryStage.show();
	}

}
