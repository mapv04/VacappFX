package UserManagement.Controllers;

import UserManagement.Confirmations;
import UserManagement.Models.*;
import WorkGroupManagement.Models.WorkGroup;
import WorkGroupManagement.Models.WorkGroupRead;
import WorkGroupManagement.Models.WorkGroupUpdate;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author migue
 */
public class AdminUIController implements Initializable, Confirmations {

    @FXML private TextField txtEmployeeID;
    @FXML private TextField txtEmployeeName;
    @FXML private TextField txtEmployeeLastName;
    @FXML private TextField txtEmployeeStatus;
    @FXML private ChoiceBox choiceType;

    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnCancel;
    @FXML private Button btnActivateUser;
    @FXML private Button btnDesactivateUser;

    @FXML private TableView<Employee> table;
    @FXML private TableColumn columnID;
    @FXML private TableColumn columnType;
    @FXML private TableColumn columnName;
    @FXML private TableColumn columnLastName;
    @FXML private TableColumn columnStatus;

    @FXML private TextField txtGroupID;
    @FXML private TextField txtGroupMembers;
    @FXML private TextField txtGroupName;


    @FXML private TableView<WorkGroup> tableWorkgroup;
    @FXML private TableColumn columnWorkID;
    @FXML private TableColumn columnWorkName;
    @FXML private TableColumn columnLeaderName;
    @FXML private TableColumn columnCreatedDate;
    @FXML private TableColumn columnWorkStatus;


    @FXML private Button btnEditGroup;
    @FXML private Button btnDeleteGroup;
    @FXML private Button btnShowGroup;
    @FXML private Button btnAddMember;


    ObservableList<WorkGroup> workGroupList;
    ObservableList<Employee> employeeList;
    private int tablePosition;
    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");
    Scene scene;
    Parent fxml;
    Stage stage;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeTable();
            initializeWorkTable();
        } catch (SQLException ex) {
            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        disableAllUsersBTN();

        btnDeleteGroup.setDisable(true);
        btnEditGroup.setDisable(true);
        btnAddMember.setDisable(true);
        btnShowGroup.setDisable(true);

        //add the listeners
        final ObservableList<WorkGroup> tableGroup= tableWorkgroup.getSelectionModel().getSelectedItems();
        final ObservableList<Employee> tableEmployees = table.getSelectionModel().getSelectedItems();
        tableGroup.addListener(tableGroupSelector);
        tableEmployees.addListener(tableSelector);
    }



    private void initializeTable() throws SQLException {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeList = FXCollections.observableArrayList();
        EmployeeRead.getAllEmployees(employeeList);
        table.setItems(employeeList);
    }

    private void setSelectedEmployee() {
        final Employee employee = getSelectedEmployee();
        tablePosition = employeeList.indexOf(employee);
        if (employee != null) {
            txtEmployeeID.setText(String.valueOf(employee.getId()));
            txtEmployeeName.setText(employee.getName());
            txtEmployeeLastName.setText(employee.getLastName());
            setTypeChoiceBox(employee);
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
            btnCancel.setDisable(false);
            if(employee.getStatus()==1){
                txtEmployeeStatus.setText("ACTIVE");
                btnActivateUser.setDisable(true);
                btnDesactivateUser.setDisable(false);
            }
            else{
                txtEmployeeStatus.setText("INACTIVE");
                btnActivateUser.setDisable(false);
                btnDesactivateUser.setDisable(true);
            }

        }
    }


    private Employee getSelectedEmployee() {
        if (table != null) {
            List<Employee> employeeList = table.getSelectionModel().getSelectedItems();
            if (employeeList.size() == 1) {
                final Employee employeeSelected = employeeList.get(0);
                return employeeSelected;
            }
        }
        return null;
    }



    private final ListChangeListener<Employee> tableSelector = new ListChangeListener<Employee>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Employee> c) {
            setSelectedEmployee();
        }
    };




    private void initializeWorkTable()throws SQLException{
        columnWorkID.setCellValueFactory(new PropertyValueFactory<>("workGroupID"));
        columnWorkName.setCellValueFactory(new PropertyValueFactory<>("workGroupName"));
        columnLeaderName.setCellValueFactory(new PropertyValueFactory<>("leaderName"));
        columnCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        columnWorkStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        workGroupList=FXCollections.observableArrayList();
        WorkGroupRead.getAllWorkgroups(workGroupList);
        tableWorkgroup.setItems(workGroupList);

    }

    private final ListChangeListener<WorkGroup> tableGroupSelector= new ListChangeListener<WorkGroup>() {
        @Override
        public void onChanged(Change<? extends WorkGroup> c) {
            setSelectedWorkGroup();
        }
    };

    private void setSelectedWorkGroup(){
        final WorkGroup group = getSelectedWorkGroup();
        tablePosition = workGroupList.indexOf(group);
        if (group != null) {
            txtGroupID.setText(String.valueOf(group.getWorkGroupID()));
            txtGroupName.setText(group.getWorkGroupName());
            txtGroupMembers.setText(String.valueOf(WorkGroupRead.getMembersCount(group.getWorkGroupID())));
            txtEmployeeStatus.setText(String.valueOf(group.getStatus()));
            btnEditGroup.setDisable(false);
            btnDeleteGroup.setDisable(false);
            btnShowGroup.setDisable(false);
            btnAddMember.setDisable(false);
        }
    }

    private WorkGroup getSelectedWorkGroup(){
        if (tableWorkgroup != null) {
            List<WorkGroup> workGroupList = tableWorkgroup.getSelectionModel().getSelectedItems();
            if (workGroupList.size() == 1) {
                final WorkGroup groupSelected = workGroupList.get(0);
                return groupSelected;
            }
        }
        return null;
    }





    @FXML
    private void btnDeleteAction(ActionEvent event) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete this employee?");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            int employeeID = Integer.parseInt(txtEmployeeID.getText());
            EmployeeDelete.deleteEmployee(employeeID);
            employeeList.remove(tablePosition);
            clearText();

        }
    }


    @FXML
    private void btnEditAction(ActionEvent event) throws SQLException {
        Employee modifiedEmployee = new Employee();
        modifiedEmployee.setName(txtEmployeeName.getText());
        modifiedEmployee.setLastName(txtEmployeeLastName.getText());
        modifiedEmployee.setId(Integer.parseInt(txtEmployeeID.getText()));
        modifiedEmployee.setStatus(Integer.parseInt(txtEmployeeStatus.getText()));

        modifiedEmployee.setType(getStatus());
        if (confirmChanges(editEmployee)) {
            EmployeeUpdate.modifyEmployee(modifiedEmployee);
            employeeList.set(tablePosition, modifiedEmployee);
            clearText();
        }
    }

    @FXML
    private void btnActivateEmployeeAction(ActionEvent event){
        if(confirmChanges(activateUser)){
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(txtEmployeeID.getText()));
            employee.setStatus(1);
            employee.setType(getStatus());
            employee.setName(txtEmployeeName.getText());
            employee.setLastName(txtEmployeeLastName.getText());
            EmployeeHandleStatus.activateUser(Integer.parseInt(txtEmployeeID.getText()));
            employeeList.set(tablePosition,employee);
            clearText();
        }
    }

    @FXML
    private void btnDesactivateEmployeeAction(ActionEvent event){
        if(confirmChanges(desactivateUser)){
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(txtEmployeeID.getText()));
            employee.setStatus(0);
            employee.setType(getStatus());
            employee.setName(txtEmployeeName.getText());
            employee.setLastName(txtEmployeeLastName.getText());
            EmployeeHandleStatus.activateUser(Integer.parseInt(txtEmployeeID.getText()));
            employeeList.set(tablePosition,employee);
            clearText();
        }
    }

    @FXML
    private void btnCancelAction(){
        clearText();
    }

    @FXML
    private void btnNewGroupAction(ActionEvent event){
        try {
            fxml = FXMLLoader.load(getClass().getResource("/WorkGroupManagement/Views/WorkGroupRegister.fxml"));
            scene = new Scene(fxml);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println("ERROR in method AdminUIController.newGroupAction error: "+e);
        }
    }

    @FXML
    private void btnEditGroupAction(ActionEvent event){
        WorkGroup groupModified= new WorkGroup();
        groupModified.setWorkGroupID(Integer.parseInt(txtGroupID.getText()));
        groupModified.setWorkGroupName(txtGroupName.getText());
        groupModified.setLeaderName((String) columnLeaderName.getCellObservableValue(tablePosition).getValue());
        groupModified.setCreatedDate((LocalDate) columnCreatedDate.getCellObservableValue(tablePosition).getValue());
        groupModified.setStatus((Integer) columnStatus.getCellObservableValue(tablePosition).getValue());
        if (confirmChanges(editWorkGroup)) {
            WorkGroupUpdate.editGroup(groupModified);
            workGroupList.set(tablePosition, groupModified);
            clearText();
        }
        WorkGroupUpdate.editGroup(groupModified);
    }

    private int getStatus(){
        switch (choiceType.getValue().toString()) {
            case "Admin":
                return 0;

            case "Supervisor":
                return 1;

            case "Employee":
                return 2;

        }
        return -1;
    }

    private void setTypeChoiceBox(Employee employee){
        switch (employee.getType()) {
            case 0:
                choiceType.setValue("Admin");
                break;
            case 1:
                choiceType.setValue("Supervisor");
                break;
            case 2:
                choiceType.setValue("Employee");
                break;
        }
    }

    private void clearText() {
        txtEmployeeID.clear();
        txtEmployeeName.clear();
        txtEmployeeLastName.clear();
        txtEmployeeStatus.clear();
        txtGroupMembers.clear();
        txtGroupName.clear();
        txtGroupID.clear();
        disableAllUsersBTN();
    }
    private void disableAllUsersBTN(){
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        btnActivateUser.setDisable(true);
        btnDesactivateUser.setDisable(true);
    }


    @Override
    public boolean confirmChanges(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(message);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            return true;
        } else if (result.get() == buttonTypeNo) {
            return false;

        }
        return false;
    }


}
