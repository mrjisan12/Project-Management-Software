package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uap.DataHandler;
import uap.User;

public class AdminViewUserController implements Initializable{
	

	@FXML 
	private TextField nameTf;
	
	@FXML 
	private TextField ageTf;
	
	@FXML 
	private CheckBox adminCB;
	
	
	
	
	@FXML 
	private TableView<User> userTable;

	@FXML 
	private TableColumn<User, String> idCol;

	@FXML 
	private TableColumn<User, String> nameCol;

	@FXML 
	private TableColumn<User, Integer> ageCol;

	@FXML 
	private TableColumn<User, String> isAdminCol;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
	    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
	    ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
	    isAdminCol.setCellValueFactory(cellData -> {
	        if (cellData.getValue().isAdmin()) {   
	            return new SimpleStringProperty("Admin");
	        } else {
	            return new SimpleStringProperty("User");
	        }
	    });
	        	
	    showUsers();
	}

	// Method to show users in the table
	private void showUsers() {
	    ArrayList<User> users = Main.pManager.getUsers();
	    ObservableList<User> userList = FXCollections.observableArrayList(users);
	    userTable.setItems(userList);
	}
  
    
    
    @FXML
	public void addUser(ActionEvent event) throws IOException {
	    String name = nameTf.getText();
	    String ageText = ageTf.getText();
	    boolean isAdmin = adminCB.isSelected();

	    try {
	        int age = Integer.parseInt(ageText);
	        Main.pManager.addUser(name, age, isAdmin);
	        DataHandler.saveData(Main.pManager);
	       // System.out.println("User Added Successfully");
	        JOptionPane.showMessageDialog(null, "User Added Successfully");
	     //   System.out.println(Main.pManager.getUsers());
	        showUsers();
	    } catch (NumberFormatException e) {
	        System.err.println("Invalid age input: " + ageText);
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
