package Login.Controllers;


import Login.Models.Implementations.*;
import UserManagement.Controllers.AdminUIController;
import VacationManagementEmployee.Controllers.EmployeeUIController;
import VacationManagementSupervisor.Controllers.SupervisorUIController;
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

    @FXML
    private AnchorPane panel;
    @FXML
    private Pane content_area;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    Alert alert = new Alert(AlertType.ERROR);
    int counterFalses = 0;
    Scene scene;
    Parent fxml;
    Stage stage;
    EmployeeSearch employeeSearch = new EmployeeSearch();
    EmployeeValidation employeeValidation = new EmployeeValidation();

    @FXML
    private void btnLoginAction(ActionEvent event) throws SQLException, IOException {
        String user = txtUser.getText();
        String password = txtPassword.getText();

        if (user.length() == 0 || password.length() == 0) {
            alert.setTitle("Error Login");
            alert.setHeaderText("Employee or password incorrect");
            alert.show();
        } else {
            Employee userData = employeeValidation.employeeExist(user);
            if (userData == null) {
                alert.setTitle("Error Login");
                alert.setHeaderText("The user doesn't exist");

                alert.show();
            } else {
                if (userData.getStatus() != 1) {
                    alert.setTitle("Error Login");
                    alert.setHeaderText("This user has been desactivated");
                    alert.setContentText("");
                    alert.show();
                } else {

                    if (!password.equals(userData.getPassword())) {
                        counterFalses++;
                        alert.setTitle("Error Login");
                        alert.setHeaderText("Employee or password incorrect");
                        alert.setContentText("Incorrect password \n failed attempts " + counterFalses
                                + "\n after the 3rd attempt the user blocks");
                        alert.show();
                        if (counterFalses == 3) {
                            employeeValidation.blockUser(user);
                            alert.setTitle("Error Login");
                            alert.setHeaderText("The user has been blocked ,please contact an administrator to unlock the user");
                            alert.setContentText("");
                            alert.show();
                        }
                    } else {
                        switch (userData.getType()) {//this send the user to the screen that he belongs
                            case 0:
                                AdminUIController.setUserID(userData.getId());
                                fxml = FXMLLoader.load(getClass().getResource("/UserManagement/Views/AdminUI.fxml"));
                                scene = new Scene(fxml);
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                                break;

                            case 1:

                                SupervisorUIController.setSupervisorID(employeeSearch.searchEmployeeUserName(user));
                                fxml = FXMLLoader.load(getClass().getResource("/VacationManagementSupervisor/Views/SupervisorUI.fxml"));
                                scene = new Scene(fxml);
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                                break;

                            case 2:
                                employeeSearch = new EmployeeSearch();
                                EmployeeUIController.setEmployeeID(employeeSearch.searchEmployeeUserName(user));
                                fxml = FXMLLoader.load(getClass().getResource("/VacationManagementEmployee/Views/EmployeeUI.fxml"));
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
        }
    }


    @FXML
    private void btnRegisterAction(ActionEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("/Login/Views/RegistrationUI.fxml"));
        scene = new Scene(fxml);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnRecoverAction(ActionEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("/Login/Views/RecoverUI.fxml"));
        scene = new Scene(fxml);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}