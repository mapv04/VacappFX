package WorkGroupManagement.Controllers;

import UserManagement.Models.Abstracts.AEmployee;
import UserManagement.Models.Abstracts.IEmployeeRead;
import UserManagement.Models.Implementations.EmployeeRead;
import WorkGroupManagement.Models.Abstracts.IWorkGroupRead;
import WorkGroupManagement.Models.Abstracts.IWorkGroupUpdate;
import WorkGroupManagement.Models.Implementations.WorkGroup;
import WorkGroupManagement.Models.Implementations.WorkGroupRead;
import WorkGroupManagement.Models.Implementations.WorkGroupUpdate;
import WorkGroupManagement.Values.Strings;
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
public class WorkGroupRegisterController implements Initializable {

    @FXML private TableView<AEmployee> table;
    @FXML private TableColumn columnSupervisorID;
    @FXML private TableColumn columnSupervisorName;
    @FXML private TableColumn columnSupervisorStatus;
    @FXML private TextField txtGroupName;
    @FXML private Button btnCancel;
    @FXML private Button btnCreate;

    private ObservableList<AEmployee> supervisorsList;
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

        final ObservableList<AEmployee> supervisorList= table.getSelectionModel().getSelectedItems();
        supervisorList.addListener(tableSelector);

    }

    @FXML
    private void btnCreateAction(){
            String name=toUpperCase(txtGroupName.getText());
            IWorkGroupRead workGroupRead= new WorkGroupRead();
            if(name != null) {
                if (!workGroupRead.ifGroupExist(name)) {
                    LocalDate currentDate = LocalDate.now();
                    AEmployee leader = getSelected();
                    WorkGroup newGroup = new WorkGroup();
                    newGroup.setWorkGroupName(name);
                    newGroup.setFkLeaderID(leader.getId());
                    newGroup.setLeaderName(leader.getName());
                    newGroup.setCreatedDate(currentDate);
                    newGroup.setStatus(1);
                    IWorkGroupUpdate workGroupUpdate= new WorkGroupUpdate();
                    workGroupUpdate.createGroup(newGroup);
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


    public void initializeTable(){
        columnSupervisorID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnSupervisorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSupervisorStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        supervisorsList= FXCollections.observableArrayList();
        IEmployeeRead employeeRead= new EmployeeRead();
        employeeRead.getAllSupervisors(supervisorsList);
        table.setItems(supervisorsList);

    }


    public AEmployee getSelected() {
        if (table != null) {
            List<AEmployee> supervisorList = table.getSelectionModel().getSelectedItems();
            if (supervisorList.size() == 1) {
                final AEmployee supervisorSelected = supervisorList.get(0);
                return supervisorSelected;
            }
        }
        return null;

    }

    private final ListChangeListener<AEmployee> tableSelector = new ListChangeListener<AEmployee>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends AEmployee> c) {
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

    public boolean confirmLogout(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        try {
            if (result.get() == ButtonType.OK) {
                alert.close();
                return true;
            }
            else {
                alert.close();
                return false;
            }
        }catch (NoSuchElementException e){
            alert.close();
            return false;
        }
    }

    @FXML
    private void btnLogoutAction(ActionEvent event) {
        if (confirmLogout(Strings.logout)) {
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



}
