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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uap.Apartment;
import uap.DataHandler;
import uap.NotAvailableException;
import uap.Property;
import uap.PropertyManager;
import uap.User;

public class AdminViewController implements Initializable{
	
	
	// For Search a Property
	
	@FXML
    private TextField bedTf;
	
	@FXML
	private TextField washTf;
	 
	@FXML
	private TextField locTf;

	@FXML
	private TextField floorspTf;

	
	// For Lease a Property
	
	@FXML
	private TextField propId;
	 
	@FXML
	private TextField userId;

	@FXML
	private DatePicker datePick;
	 
	@FXML
	private TextField durationTf;
	
	
	

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
	
	@FXML
    private TableColumn<Apartment, String> isAvailableCol;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
	    idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
	    locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
	    floorSpaceCol.setCellValueFactory(new PropertyValueFactory<>("FloorSpace"));
	    bedCol.setCellValueFactory(new PropertyValueFactory<>("NoOfBed"));
	    washrCol.setCellValueFactory(new PropertyValueFactory<>("NoOfWashRoom"));
	    rentCol.setCellValueFactory(new PropertyValueFactory<>("Rent"));
	    genaratorCol.setCellValueFactory(cellData -> {
	        if (cellData.getValue().hasGenerator()) 
	        {   
	            return new SimpleStringProperty("Yes");
	        } 
	        else 
	        {
	            return new SimpleStringProperty("No");
	        }
	    });
	    isAvailableCol.setCellValueFactory(cellData -> {
	        if (cellData.getValue().isAvailable()) 
	        {   
	            return new SimpleStringProperty("Yes");
	        } 
	        else 
	        {
	            return new SimpleStringProperty("No");
	        }
	    });
	        	
	    showAppartments();
	}

	// Method to show users in the table
	private void showAppartments() {
	    ArrayList<Apartment> apartments = null;
		try {
			apartments = Main.pManager.getAppartments();
		} catch (NotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ObservableList<Apartment> appartmentList = FXCollections.observableArrayList(apartments);
	    userTable.setItems(appartmentList);
	}
	
	
	/*
	private void SearchResults() {
	    ArrayList<Apartment> apartments = null;
	    int bedroom = Integer.parseInt(bedTf.getText());
	    int washroom = Integer.parseInt(washTf.getText());
	    String location = locTf.getText();
	    Double floorSpace = Double.parseDouble(floorspTf.getText());
		try {
			apartments = Main.pManager.getAppartments(location,bedroom,washroom,floorSpace);
		} catch (NotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please Filup all The fields");
		}
	    ObservableList<Apartment> appartmentList = FXCollections.observableArrayList(apartments);
	    userTable.setItems(appartmentList);
	}
  	*/
	
	
	
	
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
	
	
	
	//private PropertyManager propertyManager;
	
	// Lease Property Code
	
	
	public void AdminLeaseProperty(String propertyId, String userId, String startDate, int durationInMonth) 
	{
        Property property = null;
        User leaseFor = null;

        // Find property and user by their IDs
        for (Property prop : Main.pManager.getProperties()) {
            if (prop.getId().equalsIgnoreCase(propertyId)) {
                property = prop;
                break;
            }
        }

        for (User user : Main.pManager.getUsers()) {
            if (user.getId().equalsIgnoreCase(userId)) {
                leaseFor = user;
                break;
            }
        }

        // If property or user not found, throw an exception
        if (property == null || leaseFor == null) {
            throw new IllegalArgumentException("Property or user not found");
        }

        try {
            // Lease the property
        //	String note = "Nice";
            Main.pManager.leaseProperty(property, leaseFor, startDate, durationInMonth);
          //  String note = "nice";
         //   Main.pManager.LeaseRecord.setNote(note);
            // need to save data
            try {
				DataHandler.saveData(Main.pManager);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            JOptionPane.showMessageDialog(null, "Property leased successfully");
          //  System.out.println("Property leased successfully");
        } catch (NotAvailableException e) {
            //System.err.println("Error leasing property: " + e.getMessage());
        	JOptionPane.showMessageDialog(null, "Sorry! Property is already rented");
        }
        // refresh table new for new updated data
        showAppartments();
    }
	
	
	
	
	@FXML
	public void Search(ActionEvent event) throws IOException {
		SearchResults();
	}
	
	@FXML
	public void Lease(ActionEvent event) throws IOException {
	    String pid = propId.getText();
	    String uid = userId.getText();
	    LocalDate selectedDate = datePick.getValue();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
	    String date = selectedDate.format(formatter);
	    int duration = Integer.parseInt(durationTf.getText());
	//    System.out.println(pid + " " + uid + " " + date + " " + duration);
	    AdminLeaseProperty(pid, uid, date, duration);
	 //   System.out.println(Main.pManager.getRecords());
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
