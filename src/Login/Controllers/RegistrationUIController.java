package Login.Controllers;

import Login.Models.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        comboType.getSelectionModel().select(0);
    }


    @FXML
    private void btnRegisterUser() throws SQLException, IOException {//this method add the new employee
        Employee employee = new Employee();

        int pass = 0;

        if (!name.getText().isEmpty()) {
            employee.setName(name.getText());
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your name is very short. please enter it full");
            alert.show();
            pass = 0;
        }
        if (!lastName.getText().isEmpty()) {
            employee.setLastName(lastName.getText());
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your last name is very short. please enter it full");
            alert.show();
            pass = 0;
        }
        if (!username.getText().isEmpty()) {
            employee.setUsername(username.getText());
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your username is very short. please enter it full");
            alert.show();
            pass = 0;
        }
        if (isEmailCorrect(email.getText())) {
            employee.setEmail(email.getText());
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your email is not correct. please enter it full");
            alert.show();
            pass = 0;
        }
        if (!password.getText().isEmpty()) {
            employee.setPassword(password.getText());
            employee.setStatus(1);
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your password is very short. please enter it full");
            alert.show();
            pass = 0;
        }


        if (!comboType.getSelectionModel().isEmpty()) {
            switch (comboType.getValue().toString()) {//compare which type the employee is
                case "Admin":
                    employee.setType(0);
                    pass++;
                    break;
                case "Supervisor":
                    employee.setType(1);
                    pass++;
                    break;
                case "Employee":
                    employee.setType(2);
                    pass++;
                    break;
                default:
                    alert.setTitle("Error incomplete data");
                    alert.setHeaderText("Ups you have not selected what type of employee you are. please enter it full");
                    alert.show();
                    pass = 0;
                    break;
            }
        }

        if (pass == 6) {
            if (!EmployeeSearch.searchEmployeeExists(employee)) {
                EmployeeCreate.addNewEmployee(employee);//add the new employee
                Parent fxml = FXMLLoader.load(getClass().getResource("/Login/Views/LoginUI.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } else {
                alert.setTitle("Error Login");
                alert.setHeaderText("This employee already exist");
                alert.show();
            }
        }

    }


    public boolean isEmailCorrect(String email) {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher matches = pattern.matcher(email);
        return matches.find();
    }


}