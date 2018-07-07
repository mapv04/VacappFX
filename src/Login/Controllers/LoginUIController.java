package Login.Controllers;

import VacationManagement.Controllers.EmployeeUIController;
import VacationManagement.Controllers.SupervisorUIController;
import Login.Models.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author migue
 */
public class LoginUIController implements Initializable {

    @FXML private AnchorPane panel;
    @FXML private Pane content_area;
    @FXML private TextField txtUser;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    Alert alert = new Alert(AlertType.ERROR);
    Scene scene;
    Parent fxml;
    Stage stage;

    @FXML
    private void btnLoginAction(ActionEvent event) throws SQLException, IOException {
        String user = txtUser.getText();
        String password = txtPassword.getText();

        if (user.length() == 0 || password.length() == 0) {
            alert.setTitle("Error Login");
            alert.setHeaderText("Employee or password incorrect");
            alert.show();
        } else if (!EmployeeValidation.validateEmployeeExists(user, password)) {
            alert.setTitle("Error Login");
            alert.setHeaderText("Employee or password incorrect");
            alert.show();
        } else {
            if (!EmployeeValidation.validateStatus(user)) {
                alert.setTitle("Error Login");
                alert.setHeaderText("This user has been desactivated");
                alert.show();
            } else {
                switch (EmployeeValidation.validateType(user)) {//this send the user to the screen that he belongs
                    case 0:
                        fxml = FXMLLoader.load(getClass().getResource("/UserManagement/Views/AdminUI.fxml"));
                        scene = new Scene(fxml);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                        break;

                    case 1:
                        SupervisorUIController.setSupervisorID(EmployeeSearch.searchEmployeeUserName(user));
                        fxml = FXMLLoader.load(getClass().getResource("/VacationManagement/Views/SupervisorUI.fxml"));
                        scene = new Scene(fxml);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                        break;

                    case 2:
                        EmployeeUIController.setEmployeeID(EmployeeSearch.searchEmployeeUserName(user));
                        fxml = FXMLLoader.load(getClass().getResource("/VacationManagement/Views/EmployeeUI.fxml"));
                        scene = new Scene(fxml);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                        break;

                    default:
                        break;
                }
            }

        }

    }

    @FXML
    private void btnRegisterAction(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Login/Views/RegistrationUI.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
