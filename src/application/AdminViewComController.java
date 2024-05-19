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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uap.Apartment;
import uap.CommercialSpace;
import uap.DataHandler;
import uap.NotAvailableException;
import uap.Property;
import uap.User;

public class AdminViewComController implements Initializable {
	
	// For Search
	
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
	        if (cellData.getValue().isAvailable()) 
	        {   
	            return new SimpleStringProperty("Yes");
	        } 
	        else 
	        {
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
        	//String note = "Good";
            Main.pManager.leaseProperty(property, leaseFor, startDate, durationInMonth);
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
        
        showCommercials();
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
