/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vacappfx;

import Controllers.UserController;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class RegistrationUIController implements Initializable {
    
    @FXML private TextField name;
    @FXML private TextField lastName;
    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private ChoiceBox comboType;
    @FXML private Pane content_area;
    Alert alert = new Alert(Alert.AlertType.ERROR);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    private void btnRegisterUser() throws SQLException, IOException{//this method add the new user
        User user= new User();
        UserController userCon= new UserController();
        user.setName(name.getText());
        user.setLastName(lastName.getText());
        user.setUsername(username.getText());
        user.setEmail(email.getText());
        user.setPassword(password.getText());
        user.setStatus(1);
        switch(comboType.getValue().toString()){//compare which type the user is 
            case "Manager":
                user.setType(0);
                break;
            case "Supervisor":
                user.setType(1);
                break;
            case "Employee":
                user.setType(2);
                break;
            default:
                //TODO  show a messagge
                break;
                
        }
        if(!userCon.searchUserExist(user)){
            userCon.addNewUser(user);
            Parent fxml= FXMLLoader.load(getClass().getResource("LoginUI.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        }
        else{
            alert.setTitle("Error Login");
            alert.setHeaderText("This user already exist");
            alert.show();
        }
            
    }
    
}
