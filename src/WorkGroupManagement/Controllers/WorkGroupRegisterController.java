package WorkGroupManagement.Controllers;

import WorkGroupManagement.Interfaces.Tables;
import UserManagement.Models.Employee;
import UserManagement.Models.EmployeeRead;
import WorkGroupManagement.Values.Strings;
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
public class WorkGroupRegisterController implements Initializable, Tables {

    @FXML private TableView<Employee> table;
    @FXML private TableColumn columnSupervisorID;
    @FXML private TableColumn columnSupervisorName;
    @FXML private TableColumn columnSupervisorStatus;
    @FXML private TextField txtGroupName;
    @FXML private Button btnCancel;
    @FXML private Button btnCreate;

    private ObservableList<Employee> supervisorsList;
    Stage stage;
    Scene scene;
    Parent fxml;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearAll();
        initializeTable();

        final ObservableList<Employee> supervisorList= table.getSelectionModel().getSelectedItems();
        supervisorList.addListener(tableSelector);

    }

    @FXML
    private void btnCreateAction(){
            String name=toUpperCase(txtGroupName.getText());
            if(name != null) {
                if (!WorkGroupRead.ifGroupExist(name)) {
                    LocalDate currentDate = LocalDate.now();
                    Employee leader = getSelected();
                    WorkGroup newGroup = new WorkGroup();
                    newGroup.setWorkGroupName(name);
                    newGroup.setFkLeaderID(leader.getId());
                    newGroup.setLeaderName(leader.getName());
                    newGroup.setCreatedDate(currentDate);
                    newGroup.setStatus(1);
                    WorkGroupUpdate.createGroup(newGroup);
                    groupCreated();
                    clearAll();
                } else {
                    groupExist();
                }
            }

    }

    @FXML
    private void btnCancelAction(){
        clearAll();
    }

    @FXML
    private void btnBackAction(ActionEvent event){
        try {
            fxml = FXMLLoader.load(getClass().getResource("/UserManagement/Views/AdminUI.fxml"));
            scene = new Scene(fxml);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println("ERROR in method WorkGroupRegisterController.bntBackAction error: "+e);
        }
    }


    @Override
    public void initializeTable(){
        columnSupervisorID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnSupervisorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSupervisorStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        supervisorsList= FXCollections.observableArrayList();
        EmployeeRead.getAllSupervisors(supervisorsList);
        table.setItems(supervisorsList);

    }

    @Override
    public Employee getSelected() {
        if (table != null) {
            List<Employee> supervisorList = table.getSelectionModel().getSelectedItems();
            if (supervisorList.size() == 1) {
                final Employee supervisorSelected = supervisorList.get(0);
                return supervisorSelected;
            }
        }
        return null;

    }

    private final ListChangeListener<Employee> tableSelector = new ListChangeListener<Employee>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Employee> c) {
            getSelected();
            btnCancel.setDisable(false);
            btnCreate.setDisable(false);
        }
    };

    private void clearAll(){
        txtGroupName.clear();
        btnCancel.setDisable(true);
        btnCreate.setDisable(true);
    }

    private void groupCreated(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(Strings.createGroupSucced);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            alert.close();
            initializeTable();
        }
        else
            initializeTable();
            alert.close();
    }

    private void groupExist(){
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(Strings.groupExist);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
            } else {
                alert.close();
            }
        }catch(NoSuchElementException e){

        }finally{
            txtGroupName.clear();
        }
    }

    private void restriction(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
        return str.matches("^[^\\d].*");
    }


}
