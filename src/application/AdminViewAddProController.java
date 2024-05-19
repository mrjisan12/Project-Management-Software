package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uap.DataHandler;

public class AdminViewAddProController {
	
	
	 @FXML
	    private TextField floorAppTf;

	    @FXML
	    private TextField floorComTf;

	    @FXML
	    private CheckBox hasfCB;

	    @FXML
	    private CheckBox hasgCB;

	    @FXML
	    private TextField locationAppTf;

	    @FXML
	    private TextField locationComTf;

	    @FXML
	    private TextField nbTf;

	    @FXML
	    private TextField nwTf;

	    @FXML
	    private TextField rentAppTf;

	    @FXML
	    private TextField rentComTf;
	
	
	
	
	@FXML
	public void addProperty(ActionEvent event) throws IOException {
	    String location = locationAppTf.getText();
	    String rents = rentAppTf.getText();
	    String floorSpaces = floorAppTf.getText();
	    String nobs = nbTf.getText();
	    String nows = nwTf.getText();
	    boolean hasG = hasgCB.isSelected();

	    try {
	        Double rent = Double.parseDouble(rents);
	        Double floorSpace = Double.parseDouble(floorSpaces);
	        int nob = Integer.parseInt(nobs);
	        int now = Integer.parseInt(nows);
	        Main.pManager.addProperty(location, rent, floorSpace,nob,now,hasG);
	        DataHandler.saveData(Main.pManager);
	      //  System.out.println("Appartment Added Successfully");
	        JOptionPane.showMessageDialog(null, "Appartment Added Successfully");
	    } catch (NumberFormatException e) {
	        System.err.println("Invalid age input: " + e.getMessage());
	    }
	}
	
	
	
	@FXML
	public void addCommercial(ActionEvent event) throws IOException {
		String location = locationComTf.getText();
	    String rents = rentComTf.getText();
	    String floorSpaces = floorComTf.getText();
	    boolean hasF = hasfCB.isSelected();

	    try {
	    	Double rent = Double.parseDouble(rents);
	        Double floorSpace = Double.parseDouble(floorSpaces);
	        Main.pManager.addProperty(location, rent, floorSpace,hasF);
	        DataHandler.saveData(Main.pManager);
	       // System.out.println("Commercial Space Added Successfully");
	        JOptionPane.showMessageDialog(null, "Commercial Space Added Successfully");
	    } catch (NumberFormatException e) {
	        System.err.println("Invalid age input: " + e.getMessage());
	    }
	}
	
	
	
	

	@FXML
	public void AdminViewAppartment(ActionEvent event) throws IOException {
		loadScene("AdminViewAno.fxml");
	}
	
	@FXML
	public void AdminViewCommercial(ActionEvent event) throws IOException {
		loadScene("AdminViewCom.fxml");

	}
	
	@FXML
	public void AdminViewLease(ActionEvent event) throws IOException {
		loadScene("AdminViewLease.fxml");
	}
	
	@FXML
	public void AdminViewAddUser(ActionEvent event) throws IOException {
		loadScene("AdminViewUser.fxml");
	}
	
	@FXML
	public void AdminViewAddProperty(ActionEvent event) throws IOException {
		loadScene("AdminViewAddProperty.fxml");
	}
    

	@FXML
	public void logout(ActionEvent event) throws IOException {
		Pane root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		Scene scene = new Scene(root);
		Main.primaryStage.setScene(scene);
		Main.primaryStage.setTitle("Property Management Software");
		Image icon = new Image("/images/icon.png");
		Main.primaryStage.getIcons().add(icon);
		Main.primaryStage.show();
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
