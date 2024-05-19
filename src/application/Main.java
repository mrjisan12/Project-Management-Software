package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import uap.DataHandler;
import uap.PropertyManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class Main extends Application  {
	//public static Object stage;
	public static Stage primaryStage;
	public static PropertyManager pManager = new PropertyManager("Jisan");

	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		try {
			
			
			Pane root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Property Management Software");
			Image icon = new Image("/images/icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			 e.printStackTrace();
			//System.out.println("Start e error");
		}
	}
	
	public static void main(String[] args) {
		try {
			pManager = DataHandler.loadData();
		} catch (ClassNotFoundException | IOException e) {
			//System.out.println("Ekhane error");
			 e.printStackTrace();
		}
		launch(args);
	}
}
