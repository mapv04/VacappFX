package UserManagement.Controllers;

import UserManagement.Models.Abstracts.*;
import UserManagement.Models.Implementations.*;
import UserManagement.Values.Strings;
import WorkGroupManagement.Controllers.AddMembersController;
import WorkGroupManagement.Controllers.ShowMembersController;
import WorkGroupManagement.Controllers.WorkGroupRegisterController;
import WorkGroupManagement.Models.Abstracts.*;
import WorkGroupManagement.Models.Implementations.*;
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
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author migue
 */
public class AdminUIController implements Initializable {

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

    @FXML private TableView<AEmployee> table;
    @FXML private TableColumn columnID;
    @FXML private TableColumn columnType;
    @FXML private TableColumn columnName;
    @FXML private TableColumn columnLastName;
    @FXML private TableColumn columnStatus;

    @FXML private TextField txtGroupID;
    @FXML private TextField txtGroupMembers;
    @FXML private TextField txtGroupName;


    @FXML private TableView<AWorkGroup> tableWorkgroup;
    @FXML private TableColumn columnWorkID;
    @FXML private TableColumn columnWorkName;
    @FXML private TableColumn columnLeaderName;
    @FXML private TableColumn columnCreatedDate;
    @FXML private TableColumn columnWorkStatus;


    @FXML private Button btnEditGroup;
    @FXML private Button btnDeleteGroup;
    @FXML private Button btnShowGroup;
    @FXML private Button btnAddMember;
    @FXML private Button btnActivateGroup;
    @FXML private Button btnDesactivateGroup;
    @FXML private Button btnChangeLeader;

    private static int userID;

    ObservableList<AWorkGroup> workGroupList;
    ObservableList<AEmployee> employeeList;
    private int tablePosition;
    Scene scene;
    Parent fxml;
    Stage stage;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            initializeTable();
            initializeWorkTable();

        disableAllUsersBTN();

        disableAllGroupBTN();

        //add the listeners
        final ObservableList<AWorkGroup> tableGroup= tableWorkgroup.getSelectionModel().getSelectedItems();
        final ObservableList<AEmployee> tableEmployees = table.getSelectionModel().getSelectedItems();
        tableGroup.addListener(tableGroupSelector);
        tableEmployees.addListener(tableSelector);
    }


    public void initializeTable(){
        IEmployeeFactory employeeFactory = new EmployeeFactory();
        IEmployeeRead employeeRead = new EmployeeRead(employeeFactory.getEmployee(),employeeFactory);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeList = FXCollections.observableArrayList();
        employeeRead.getAllEmployees(employeeList);
        table.setItems(employeeList);
    }




    public void setSelected() {
        final AEmployee employee = getSelected();
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


    public AEmployee getSelected() {
        if (table != null) {
            List<AEmployee> employeeList = table.getSelectionModel().getSelectedItems();
            if (employeeList.size() == 1) {
                final AEmployee employeeSelected = employeeList.get(0);
                return employeeSelected;
            }
        }
        return null;
    }



    private final ListChangeListener<AEmployee> tableSelector = new ListChangeListener<AEmployee>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends AEmployee> c) {
            setSelected();
        }
    };

    @FXML
    private void btnDeleteAction() {
        AEmployee employee=getSelected();
        if(employee.getId()!=userID) {
            IWorkGroupRead workGroupRead = new WorkGroupRead();
            if(!workGroupRead.isLeader(employee.getId())) {
                if (confirmChanges(Strings.deleteEmployee)) {
                    int employeeID = Integer.parseInt(txtEmployeeID.getText());
                    IEmployeeDelete employeeDelete = new EmployeeDelete();
                    employeeDelete.deleteEmployee(employeeID);
                    employeeList.remove(tablePosition);
                }
            }
            else{
                restriction(Strings.isLeader);
            }
        }
        else {
            restriction(Strings.modifyAdmin);
        }
        if(employeeList.size()==0){
            clearText();
        }
    }


    @FXML
    private void btnEditAction(){
        AEmployee modifiedEmployee = getSelected();
        if(modifiedEmployee.getId()!=userID) {
            modifiedEmployee.setName(toUpperCase(txtEmployeeName.getText()));
            if (modifiedEmployee.getName() != null) {
                modifiedEmployee.setLastName(toUpperCase(txtEmployeeLastName.getText()));
                modifiedEmployee.setId(Integer.parseInt(txtEmployeeID.getText()));
                if (modifiedEmployee.getName() != null && modifiedEmployee.getLastName() != null) {
                    if (txtEmployeeStatus.getText().equals("ACTIVE"))
                        modifiedEmployee.setStatus(1);
                    else {
                        modifiedEmployee.setStatus(0);
                    }

                    modifiedEmployee.setType(getStatus());
                    if (confirmChanges(Strings.editEmployee)) {
                        IEmployeeUpdate employeeUpdate= new EmployeeUpdate();
                        employeeUpdate.modifyEmployee(modifiedEmployee);
                        employeeList.set(tablePosition, modifiedEmployee);
                        clearText();
                    }
                }
            }

        }
        else
            restriction(Strings.modifyAdmin);
    }

    @FXML
    private void btnActivateEmployeeAction(){
        AEmployee employee= getSelected();
        if(employee.getId()!=userID) {
            if (confirmChanges(Strings.activateUser)) {
                employee.setStatus(1);
                IEmployeeHandleStatus employeeHandleStatus= new EmployeeHandleStatus();
                employeeHandleStatus.activateUser(Integer.parseInt(txtEmployeeID.getText()));
                employeeList.set(tablePosition, employee);
                clearText();
            }
        }
        else
            restriction(Strings.modifyAdmin);
    }

    @FXML
    private void btnDesactivateEmployeeAction(ActionEvent event){
        AEmployee employee= getSelected();
        if(employee.getId()!=userID) {
            if (confirmChanges(Strings.desactivateUser)) {
                employee.setStatus(0);
                IEmployeeHandleStatus employeeHandleStatus= new EmployeeHandleStatus();
                employeeHandleStatus.desactivateUser(Integer.parseInt(txtEmployeeID.getText()));
                employeeList.set(tablePosition, employee);
                clearText();
            }
        }
        else
            restriction(Strings.modifyAdmin);
    }

    @FXML
    private void btnCancelAction(){
        clearText();
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

    private void setTypeChoiceBox(AEmployee employee){
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
        disableAllUsersBTN();
    }
    private void disableAllUsersBTN(){
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        btnActivateUser.setDisable(true);
        btnDesactivateUser.setDisable(true);
    }




    private void initializeWorkTable(){
        IWorkGroupFactory workGroupFactory= new WorkGroupFactory();
        IEmployeeFactory employeeFactory= new EmployeeFactory();
        columnWorkID.setCellValueFactory(new PropertyValueFactory<>("workGroupID"));
        columnWorkName.setCellValueFactory(new PropertyValueFactory<>("workGroupName"));
        columnLeaderName.setCellValueFactory(new PropertyValueFactory<>("leaderName"));
        columnCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        columnWorkStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        workGroupList=FXCollections.observableArrayList();
        IWorkGroupRead workGroupRead= new WorkGroupRead(workGroupFactory.getWorkGroup(),employeeFactory.getEmployee(),
                                                        workGroupFactory,employeeFactory,workGroupFactory.getWorkGroupData());
        workGroupRead.getAllWorkgroups(workGroupList);
        tableWorkgroup.setItems(workGroupList);

    }

    private final ListChangeListener<AWorkGroup> tableGroupSelector= new ListChangeListener<AWorkGroup>() {
        @Override
        public void onChanged(Change<? extends AWorkGroup> c) {
            setSelectedWorkGroup();
        }
    };

    private void setSelectedWorkGroup(){
        final AWorkGroup group = getSelectedWorkGroup();
        tablePosition = workGroupList.indexOf(group);
        if (group != null) {
            IWorkGroupFactory workGroupFactory= new WorkGroupFactory();
            IEmployeeFactory employeeFactory= new EmployeeFactory();
            txtGroupID.setText(String.valueOf(group.getWorkGroupID()));
            txtGroupName.setText(group.getWorkGroupName());
            IWorkGroupRead workGroupRead= new WorkGroupRead(workGroupFactory.getWorkGroup(),employeeFactory.getEmployee(),
                                                            workGroupFactory,employeeFactory,workGroupFactory.getWorkGroupData());
            txtGroupMembers.setText(String.valueOf(workGroupRead.getMembersCount(group.getWorkGroupID())));
            txtEmployeeStatus.setText(String.valueOf(group.getStatus()));
            btnEditGroup.setDisable(false);
            btnDeleteGroup.setDisable(false);
            btnShowGroup.setDisable(false);
            btnAddMember.setDisable(false);
            btnChangeLeader.setDisable(false);
            if(group.getStatus()==1) {
                btnDesactivateGroup.setDisable(false);
                btnActivateGroup.setDisable(true);
            }
            else{
                btnDesactivateGroup.setDisable(true);
                btnActivateGroup.setDisable(false);
            }

        }
    }

    private AWorkGroup getSelectedWorkGroup(){
        if (tableWorkgroup != null) {
            List<AWorkGroup> workGroupList = tableWorkgroup.getSelectionModel().getSelectedItems();
            if (workGroupList.size() == 1) {
                final AWorkGroup groupSelected = workGroupList.get(0);
                return groupSelected;
            }
        }
        return null;
    }



    @FXML
    private void btnNewGroupAction(ActionEvent event){
        WorkGroupRegisterController.setTag(0);
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
    private void btnEditGroupAction(){
        IWorkGroupFactory workGroupFactory= new WorkGroupFactory();
        AWorkGroup groupModified=workGroupFactory.getWorkGroup();
        groupModified.setWorkGroupID(Integer.parseInt(txtGroupID.getText()));
        groupModified.setWorkGroupName(txtGroupName.getText());
        groupModified.setLeaderName((String) columnLeaderName.getCellObservableValue(tablePosition).getValue());
        groupModified.setCreatedDate((LocalDate) columnCreatedDate.getCellObservableValue(tablePosition).getValue());
        groupModified.setStatus((Integer) columnStatus.getCellObservableValue(tablePosition).getValue());
        if (confirmChanges(Strings.editWorkGroup)) {
            IWorkGroupUpdate workGroupUpdate= new WorkGroupUpdate();
            workGroupUpdate.editGroup(groupModified);
            workGroupList.set(tablePosition, groupModified);
            clearGroupText();
        }
        IWorkGroupUpdate workGroupUpdate= new WorkGroupUpdate();
        workGroupUpdate.editGroup(groupModified);
    }

    @FXML
    private void btnDeleteGroupAction(){
        if(confirmChanges(Strings.deleteWorkGroup)){
            int id=Integer.parseInt(txtGroupID.getText());
            IWorkGroupDelete workGroupDelete= new WorkGroupDelete();
            workGroupDelete.deleteGroup(id);
            workGroupList.remove(tablePosition);
        }
        if(workGroupList.size()==0){
            clearGroupText();
        }
    }

    @FXML
    private void btnActivateGroupAction(){
        if(confirmChanges(Strings.activateWorkGroup)){
            AWorkGroup group= getSelectedWorkGroup();
            group.setStatus(1);
            IWorkGroupHandleStatus workGroupHandleStatus= new WorkGroupHandleStatus();
            workGroupHandleStatus.activateWorkGroup(group.getWorkGroupID());
            workGroupList.set(tablePosition,group);
            clearGroupText();
        }
    }

    @FXML
    private void btnDesactivateGroupAction(){
        if(confirmChanges(Strings.desactivateGroup)){
            AWorkGroup group= getSelectedWorkGroup();
            group.setStatus(0);
            IWorkGroupHandleStatus workGroupHandleStatus= new WorkGroupHandleStatus();
            workGroupHandleStatus.desactivateWorkGroup(group.getWorkGroupID());
            workGroupList.set(tablePosition,group);
            clearGroupText();
        }
    }

    @FXML
    private void btnShowMembersAction(ActionEvent event) {
        ShowMembersController.getStageID(Integer.parseInt(txtGroupID.getText()));
        try {
            fxml = FXMLLoader.load(getClass().getResource("/WorkGroupManagement/Views/ShowMembers.fxml"));
            scene = new Scene(fxml);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println("ERROR in method AdminUIController.btnShowMembersAction error: "+e);
        }
    }

    @FXML
    private void btnAddNewMember(ActionEvent event){
        AddMembersController.setGroupID(Integer.parseInt(txtGroupID.getText()));
        try {
            fxml = FXMLLoader.load(getClass().getResource("/WorkGroupManagement/Views/AddMembers.fxml"));
            scene = new Scene(fxml);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println("ERROR in method AdminUIController.btnAddNewMembersAction error: "+e);
        }
    }

    @FXML
    private void btnChangeLeaderAction(ActionEvent event){
        AWorkGroup group=getSelectedWorkGroup();
        WorkGroupRegisterController.setTag(1);
        WorkGroupRegisterController.setGroupID(group.getWorkGroupID());
        try {
            fxml = FXMLLoader.load(getClass().getResource("/WorkGroupManagement/Views/WorkGroupRegister.fxml"));
            scene = new Scene(fxml);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println("ERROR in method AdminUIController.btnChangeLeaderAction error: "+e);
        }
    }

    private void disableAllGroupBTN(){
        btnDeleteGroup.setDisable(true);
        btnEditGroup.setDisable(true);
        btnAddMember.setDisable(true);
        btnShowGroup.setDisable(true);
        btnActivateGroup.setDisable(true);
        btnDesactivateGroup.setDisable(true);
        btnChangeLeader.setDisable(true);
    }

    private void clearGroupText(){
        txtGroupName.clear();
        txtGroupID.clear();
        txtGroupMembers.clear();
        disableAllGroupBTN();
    }


    private boolean confirmChanges(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
            return true;
        }
        else {
            alert.close();
            return false;
        }
    }

    private void restriction(String message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Restriction");
        alert.setHeaderText(message);

        Optional<ButtonType> result = alert.showAndWait();
        try {
            if (result.get() == ButtonType.OK) {
                alert.close();
            } else
                alert.close();
        }catch(NoSuchElementException e){
            alert.close();
        }
    }

    private String toUpperCase(String str){
        if(!str.isEmpty()) {
            if(containsDigit(str)) {
                str.toLowerCase();
                return str.substring(0, 1).toUpperCase() + str.substring(1);
            }
            else{
                restriction(Strings.containsDigit);
                return null;
            }
        }
        else{
            restriction(Strings.emptyField);
            return null;
        }

    }
    private boolean containsDigit(String str){
        return str.matches("[a-zA-z]*");
    }

    @FXML
    private void btnLogoutAction(ActionEvent event) {
        if (confirmChanges(Strings.logout)) {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/Login/Views/LoginUI.fxml"));
                scene = new Scene(fxml);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("ERROR in method AdminUIController.btnLogoutAction error: " + e);
            }
        }
    }

    public static void setUserID(int id){
        userID=id;
    }

}
