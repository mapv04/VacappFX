package Login.Controllers;

import Login.Models.Implementations.Employee;
import Login.Models.Implementations.EmployeeRecover;
import Login.Models.Implementations.EmployeeSearch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RecoverUIController implements Initializable {
    @FXML private TextField txtUser;
    @FXML private TextField txtPassword;
    @FXML private TextField txtResponse;
    @FXML private ChoiceBox comboQuestion;

    Alert alert = new Alert(Alert.AlertType.ERROR);
    Parent fxml;
    Stage stage;
    Scene scene;

    @FXML
    private void btnBackLogin(ActionEvent event) throws IOException{
        fxml = FXMLLoader.load(getClass().getResource("/Login/Views/LoginUI.fxml"));
        scene = new Scene(fxml);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void btnRecover(ActionEvent event) throws IOException, SQLException {
        Employee employee = new Employee();
        String stringEmployee = txtUser.getText().toString();
        String stringPassword = txtPassword.getText().toString();
        String stringResponse = txtResponse.getText().toString();
        String stringQuestion = " ";
        int intQuestion = 0;

        int pass = 0;

        if (!txtUser.getText().isEmpty()) {
            employee.setUsername(txtUser.getText());
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your username is very short. please enter it full");
            alert.show();
        }
        if (!txtPassword.getText().isEmpty()) {
            employee.setPassword(txtPassword.getText());
            employee.setStatus(1);
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your password is very short. please enter it full");
            alert.show();
        }
        if (!txtResponse.getText().isEmpty()) {
            employee.setResponse(txtResponse.getText());
            pass++;
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups your response is very short. please enter it full");
            alert.show();
        }
        if (!comboQuestion.getSelectionModel().isEmpty()) {
            switch (comboQuestion.getValue().toString()) {//compare which is your secret question
                case "Name of your pet":
                    employee.setQuestion(1);
                    stringQuestion="1";
                    intQuestion = Integer.parseInt(stringQuestion);
                    pass++;
                    break;
                case "Name of your dad or mom":
                    employee.setQuestion(2);
                    stringQuestion="2";
                    intQuestion = Integer.parseInt(stringQuestion);
                    pass++;
                    break;
                case "Name of your first school":
                    employee.setQuestion(3);
                    stringQuestion="3";
                    intQuestion = Integer.parseInt(stringQuestion);
                    pass++;
                    break;
            }
        } else {
            alert.setTitle("Error incomplete data");
            alert.setHeaderText("Ups You have not selected which is your secret question. please enter it full");
            alert.show();
        }
        if (pass == 4) {
            EmployeeSearch searchObject = new EmployeeSearch();
            EmployeeRecover employeeRecover = new EmployeeRecover();
            if (searchObject.searchEmployeeExists(employee)) {
                employeeRecover.changePassword(stringEmployee,stringPassword,intQuestion,stringResponse);
                //objectcreate.addNewEmployee(employee);
                fxml = FXMLLoader.load(getClass().getResource("/Login/Views/LoginUI.fxml"));
                scene = new Scene(fxml);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                alert.setTitle("Error Login");
                alert.setHeaderText("This employee does not exist");
                alert.show();
            }
        }
    }
}
