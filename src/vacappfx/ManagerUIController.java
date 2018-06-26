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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML private TextField txtEmployeeType;
    @FXML private TextField txtEmployeeName;
    @FXML private TextField txtEmployeeLastName;
    @FXML private TextField txtEmployeeStatus;
    
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
            getSelectedEmployee();
        }
    };
        
        private void getSelectedEmployee(){
            /*
            TODO
            finish this method
            */
        }

}
