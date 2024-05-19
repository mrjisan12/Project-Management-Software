package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uap.CommercialSpace;
import uap.NotAvailableException;

public class UserViewComController implements Initializable {
	
	
	
	@FXML
    private TextField locTf;
	
	@FXML
    private TextField floorspTf;
	
	


	@FXML 
	private TableView<CommercialSpace> userTable;

	@FXML 
	private TableColumn<CommercialSpace, String> idCol;

	@FXML 
	private TableColumn<CommercialSpace, String> locationCol;
	
	@FXML 
	private TableColumn<CommercialSpace, Double> floorSpaceCol;

	@FXML 
	private TableColumn<CommercialSpace, Double> rentCol;

	@FXML 
	private TableColumn<CommercialSpace, String> hasFireExitrCol;
	
	@FXML
    private TableColumn<CommercialSpace, String> isAvailableCol;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
	    locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
	    floorSpaceCol.setCellValueFactory(new PropertyValueFactory<>("FloorSpace"));
	    rentCol.setCellValueFactory(new PropertyValueFactory<>("Rent"));
	    hasFireExitrCol.setCellValueFactory(cellData -> {
	        if (cellData.getValue().hasFireExit()) {   
	            return new SimpleStringProperty("Yes");
	        } else {
	            return new SimpleStringProperty("No");
	        }
	    });
	    isAvailableCol.setCellValueFactory(cellData -> {
	        if (cellData.getValue().isAvailable()) {   
	            return new SimpleStringProperty("Yes");
	        } else {
	            return new SimpleStringProperty("No");
	        }
	    });
	        	
	    showCommercials();
	}

	// Method to show users in the table
	private void showCommercials() {
	    ArrayList<CommercialSpace> comspaces = null;
		try {
			comspaces = Main.pManager.getCommercialSpaces();
		} catch (NotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ObservableList<CommercialSpace> userList = FXCollections.observableArrayList(comspaces);
	    userTable.setItems(userList);
	}
  
	
	
	
	
	

	private void SearchResults() {
	    ArrayList<CommercialSpace> commercialspc = null;
	    double floorSpace = 0;
	    String location = locTf.getText().trim();

	    try {
	        
	        if (!floorspTf.getText().isEmpty()) {
	            floorSpace = Double.parseDouble(floorspTf.getText());
	        }

	        commercialspc = Main.pManager.getCommercialSpaces(location, floorSpace);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
	        return;
	    } catch (NotAvailableException e) {
	        // Handle NotAvailableException
	        //e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Sorry! No Commercial Space Found with this Criteria");
	        return;
	    }

	    ObservableList<CommercialSpace> commercialList = FXCollections.observableArrayList(commercialspc);
	    userTable.setItems(commercialList);
	}
	
	
	
	
	
	
	@FXML
	public void Search(ActionEvent event) throws IOException {
		SearchResults();
	}
	
	
	
	

	
	@FXML
	public void UserViewAppartment(ActionEvent event) throws IOException {
		loadScene("UserViewAppart.fxml");
	}
	
	@FXML
	public void UserViewCommercial(ActionEvent event) throws IOException {
		loadScene("UserViewCom.fxml");
	}
	
	@FXML
	public void UserViewLease(ActionEvent event) throws IOException {
		loadScene("UserViewLease.fxml");
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
