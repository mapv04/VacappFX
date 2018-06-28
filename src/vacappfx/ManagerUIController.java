/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vacappfx;

import Controllers.ManagerController;
import Models.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author migue
 */
public class ManagerUIController implements Initializable {

    @FXML private TextField txtEmployeeID;
    @FXML private TextField txtEmployeeName;
    @FXML private TextField txtEmployeeLastName;
    @FXML private TextField txtEmployeeStatus;
    
    @FXML private ChoiceBox choiceType;
    
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnCancel;
    @FXML private Button btnSave;
    
    
    @FXML private TableView<User> table;
    @FXML private TableColumn columnID;
    @FXML private TableColumn columnType;
    @FXML private TableColumn columnName;
    @FXML private TableColumn columnLastName;
    @FXML private TableColumn columnStatus;
    
    ObservableList<User> employeeList;
    ManagerController managerCon=  new ManagerController();
    private int tablePosition;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeTable();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
       btnEdit.setDisable(true);
       btnDelete.setDisable(true);
       btnCancel.setDisable(true);
       btnSave.setDisable(true);
       
       //add the listeners
       final ObservableList<User> tableEmployees= table.getSelectionModel().getSelectedItems();
       tableEmployees.addListener(tableSelector);
    } 
    
    private void initializeTable()throws SQLException{
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeList=FXCollections.observableArrayList();
        managerCon.getAllEmployees(employeeList);
        table.setItems(employeeList);
    }
    
        private final ListChangeListener<User> tableSelector= new ListChangeListener<User>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends User> c) {
            setSelectedEmployee();
        }
    };
        
        private void setSelectedEmployee(){
            final User employee= getSelectedEmployee();
            tablePosition=employeeList.indexOf(employee);
            if(employee!=null){
                txtEmployeeID.setText(String.valueOf(employee.getId()));
                txtEmployeeName.setText(employee.getName());
                txtEmployeeLastName.setText(employee.getLastName());
                txtEmployeeStatus.setText(String.valueOf(employee.getStatus()));
                switch(employee.getType()){
                    case 0:
                        choiceType.setValue("Manager"); 
                        break;
                    case 1:
                        choiceType.setValue("Supervisor"); 
                        break;
                    case 2:
                        choiceType.setValue("Employee");
                        break;
                }
              
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
            
        }
        }
        
        public User getSelectedEmployee(){
            if(table!=null){
                List<User> employeeList= table.getSelectionModel().getSelectedItems();
                if(employeeList.size()==1){
                    final User employeeSelected=employeeList.get(0);
                    return employeeSelected;
                }
            }
            return null;
        }
        
        @FXML 
        private void btnEditAction(ActionEvent event) throws SQLException{
            User modifiedEmployee= new User();
            modifiedEmployee.setName(txtEmployeeName.getText());
            modifiedEmployee.setLastName(txtEmployeeLastName.getText());
            modifiedEmployee.setId(Integer.parseInt(txtEmployeeID.getText()));
            modifiedEmployee.setStatus(Integer.parseInt(txtEmployeeStatus.getText()));
            
            switch(choiceType.getValue().toString()){
                    case "Manager":
                        modifiedEmployee.setType(0);
                        break;
                    case "Supervisor":
                        modifiedEmployee.setType(1);
                        break;
                    case "Employee":
                        modifiedEmployee.setType(2);
                        break;
                }
            if(confirmChanges()){
                managerCon.modifyEmployee(modifiedEmployee);
                employeeList.set(tablePosition, modifiedEmployee);
                clearText();
            }
                
        }
        
        private boolean confirmChanges(){
           Alert alert = new Alert(AlertType.CONFIRMATION);
           alert.setTitle("Confirmation");
           alert.setContentText("Are you sure you want to modify this employee?");
           ButtonType buttonTypeYes = new ButtonType("Yes");
           ButtonType buttonTypeNo = new ButtonType("No");
           alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
           Optional<ButtonType> result = alert.showAndWait();
           if (result.get() == buttonTypeYes){
                return true;
           } else if (result.get() == buttonTypeNo) {
                return false;
            
           }
           return false;
        }
        
        private void clearText(){
            txtEmployeeID.clear();
            txtEmployeeName.clear();
            txtEmployeeLastName.clear();
            txtEmployeeStatus.clear();
            /*
            TODO
            clear the ChoiceBox
            */
        }
}
