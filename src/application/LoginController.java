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
import uap.InvalidUserException;
import uap.User;

public class LoginController {
	
	@FXML TextField idtf;
	
	@FXML TextField nameTf;
	@FXML TextField ageTf;
	@FXML CheckBox adminCB;
	
	@FXML
	public void login(ActionEvent event) throws IOException
	{
		String id = idtf.getText(); // 123
		
		
		try {
			
			if(!id.isEmpty())
			{
				User uid = Main.pManager.findUser(id);

				if(uid.isAdmin()) {
					
					loadScene("AdminViewAno.fxml");
				}
				else if(!uid.isAdmin()) {
					loadScene("UserViewAppart.fxml");
				}
				else {
					JOptionPane.showMessageDialog(null, "Failed Login");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Failed Login");
			}
			
			
			
			
			
		} catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Wrong User ID");
			//e.printStackTrace();
		}
		
		
		
		/*
		if(id.equals("123")) {
			
			loadScene("AdminViewAno.fxml");
		}
		else if(id.equals("111")) {
			loadScene("UserViewAppart.fxml");
		}
		else {
			JOptionPane.showMessageDialog(null, "Failed Login");
		}
		*/
			
	}
	@FXML
	public void signup(ActionEvent event) throws IOException
	{
		
		loadScene("SignUp.fxml");
		
	}
	
	@FXML
	public void addUser(ActionEvent event) throws IOException {
		String name = nameTf.getText();
		int age = Integer.parseInt(ageTf.getText());
		boolean isAdmin = adminCB.isSelected();
		System.out.println(Main.pManager.addUser(name, age, isAdmin));
		DataHandler.saveData(Main.pManager);
		System.out.println("User Added Successfully");
		
		if(isAdmin==true)
		{
			loadScene("AdminViewAno.fxml");
		}
		else
		{
			loadScene("UserViewAppart.fxml");
		}
		
		
		
	}
	
	
	@FXML
	public void loginPage(ActionEvent event) throws IOException {
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
