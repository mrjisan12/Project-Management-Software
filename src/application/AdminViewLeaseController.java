package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import uap.DataHandler;
import uap.InvalidUserException;
import uap.LeaseRecord;
import uap.NotAvailableException;
import uap.Property;
import uap.User;

public class AdminViewLeaseController implements Initializable{
	
	
	@FXML
    private TextField userIdTf;
	
	@FXML
	private TextField propIdTf;
	
	@FXML
	private TableView<LeaseRecord> userTable;
	
	@FXML
    private TableColumn<LeaseRecord, String> propId;
	
	@FXML
    private TableColumn<LeaseRecord, String> UserId;

	@FXML
    private TableColumn<LeaseRecord, String> startDateId;
	
    @FXML
    private TableColumn<LeaseRecord, Integer> durationId;

    @FXML
    private TableColumn<LeaseRecord, String> noteId;

    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		propId.setCellValueFactory(new PropertyValueFactory<>("PropertyId"));
		UserId.setCellValueFactory(new PropertyValueFactory<>("UserId"));
		startDateId.setCellValueFactory(new PropertyValueFactory<>("Lease_start_date"));
		durationId.setCellValueFactory(new PropertyValueFactory<>("Duration"));
		noteId.setCellValueFactory(new PropertyValueFactory<>("Note"));
		
	    showLeaseRecords();
	}

	// Method to show users in the table
	private void showLeaseRecords() {
	    ArrayList<LeaseRecord> leaserecords = null;
		leaserecords = Main.pManager.getRecords();
	    ObservableList<LeaseRecord> lrecordList = FXCollections.observableArrayList(leaserecords);
	    userTable.setItems(lrecordList);
	}
	
	
	
	

	private void SearchResults() {
	    ArrayList<LeaseRecord> leaserecord = null;
	    
	    String userId = userIdTf.getText().trim();

	    try {
	       
	    	if (userId.isEmpty()) {
	            leaserecord = Main.pManager.getRecords();
	        } else {
	            leaserecord = Main.pManager.getRecordsForUser(userId);
	        }
	        
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
	        return;
	    } catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotAvailableException e) {
	        // Handle NotAvailableException
	        //e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Sorry! Lease Record Found with this User Id");
	        return;
	    }

	    ObservableList<LeaseRecord> lrecordList = FXCollections.observableArrayList(leaserecord);
	    userTable.setItems(lrecordList);
	}
	
	
	
	
	

	public void AdminLeaseOverProperty(String propertyId) 
	{
	    Property property = null;

	    // Find property by its ID
	    for (Property prop : Main.pManager.getProperties()) {
	        if (prop.getId().equalsIgnoreCase(propertyId)) {
	            property = prop;
	            break;
	        }
	    }

	    // If property not found, throw an exception
	    if (property == null) {
	        throw new IllegalArgumentException("Property not found");
	    }

	    try {
	    	
	    	System.out.println("Property found: " + property);
	    	System.out.println(property);
	        // Lease over the property
	        Main.pManager.leaseOver(property);

	        // Save data
	        try {
				DataHandler.saveData(Main.pManager);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Refresh UI
	        showLeaseRecords();

	        // Switch scene to the apartment scene
	        try {
				loadScene("AdminViewAno.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        JOptionPane.showMessageDialog(null, "Property leased over successfully");
	    } catch (NotAvailableException e) {
	        JOptionPane.showMessageDialog(null, "Sorry! Property is not available for lease over");
	        e.printStackTrace();
	    }
	}

	
	
	
	

	@FXML
	public void LeaseOver(ActionEvent event) throws IOException {
	    String pid = propIdTf.getText();
	    AdminLeaseOverProperty(pid);
	 //   System.out.println(Main.pManager.getRecords());
	}
	
	
	

	@FXML
	public void Search(ActionEvent event) throws IOException {
		SearchResults();
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
