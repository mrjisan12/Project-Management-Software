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
import uap.Apartment;
import uap.NotAvailableException;

public class UserViewController implements Initializable{
	
	
	@FXML
    private TableColumn<Apartment, String> isAvailableCol;
	
	// For Search a Property
	
		@FXML
	    private TextField bedTf;
		
		@FXML
		private TextField washTf;
		 
		@FXML
		private TextField locTf;

		@FXML
		private TextField floorspTf;

	
	

	@FXML 
	private TableView<Apartment> userTable;

	@FXML 
	private TableColumn<Apartment, String> idCol;

	@FXML 
	private TableColumn<Apartment, String> locationCol;
	
	@FXML 
	private TableColumn<Apartment, Double> floorSpaceCol;

	@FXML 
	private TableColumn<Apartment, Integer> bedCol;
	
	@FXML 
	private TableColumn<Apartment, Integer> washrCol;
	
	@FXML 
	private TableColumn<Apartment, Double> rentCol;

	@FXML 
	private TableColumn<Apartment, String> genaratorCol;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
	    locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
	    floorSpaceCol.setCellValueFactory(new PropertyValueFactory<>("FloorSpace"));
	    bedCol.setCellValueFactory(new PropertyValueFactory<>("NoOfBed"));
	    washrCol.setCellValueFactory(new PropertyValueFactory<>("NoOfWashRoom"));
	    rentCol.setCellValueFactory(new PropertyValueFactory<>("Rent"));
	    genaratorCol.setCellValueFactory(cellData -> {
	        if (cellData.getValue().hasGenerator()) {   
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
	        	
	    showUsers();
	}

	// Method to show users in the table
	private void showUsers() {
	    ArrayList<Apartment> apartments = null;
		try {
			apartments = Main.pManager.getAppartments();
		} catch (NotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ObservableList<Apartment> userList = FXCollections.observableArrayList(apartments);
	    userTable.setItems(userList);
	}
  
   
	
	

	private void SearchResults() {
	    ArrayList<Apartment> apartments = null;
	    int bedroom = 0;
	    int washroom = 0;
	    double floorSpace = 0;
	    String location = locTf.getText().trim();

	    try {
	        if (!bedTf.getText().isEmpty()) {
	            bedroom = Integer.parseInt(bedTf.getText());
	        }
	        if (!washTf.getText().isEmpty()) {
	            washroom = Integer.parseInt(washTf.getText());
	        }
	        if (!floorspTf.getText().isEmpty()) {
	            floorSpace = Double.parseDouble(floorspTf.getText());
	        }

	        apartments = Main.pManager.getAppartments(location, bedroom, washroom, floorSpace);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
	        return;
	    } catch (NotAvailableException e) {
	        // Handle NotAvailableException
	        //e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Sorry! No Room Found with this Criteria");
	        return;
	    }

	    ObservableList<Apartment> apartmentList = FXCollections.observableArrayList(apartments);
	    userTable.setItems(apartmentList);
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
