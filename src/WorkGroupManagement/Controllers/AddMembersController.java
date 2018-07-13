package WorkGroupManagement.Controllers;

import UserManagement.Models.Employee;
import WorkGroupManagement.Interfaces.Tables;
import WorkGroupManagement.Models.WorkGroupData;
import WorkGroupManagement.Models.WorkGroupUpdate;
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

public class AddMembersController implements Initializable,Tables {
    //@FXML private TextField txtSearch;
    @FXML private TableView<Employee> table;
    @FXML private TableColumn columnID;
    @FXML private TableColumn columnName;
    @FXML private TableColumn columnEmail;
    @FXML private TableColumn columnStatus;
    @FXML private Button btnAdd;

    private static int groupID;
    private ObservableList<Employee> employeeList;
    private int tablePosition;
    Scene scene;
    Stage stage;
    Parent fxml;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();

        final ObservableList<Employee> employeeList= table.getSelectionModel().getSelectedItems();
        employeeList.addListener(tableSelector);
        btnAdd.setDisable(true);

       /* txtSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btnSearch.setDisable(false);
            }
        });*/

    }



    @Override
    public void initializeTable(){
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeList = FXCollections.observableArrayList();
        UserManagement.Models.EmployeeRead.getJustEmployeesType(employeeList);
        table.setItems(employeeList);
    }

    @Override
    public Employee getSelected() {
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
            getSelected();
            btnAdd.setDisable(false);
        }
    };

    @FXML
    private void btnAddAction(){

        if(confirmAdd(Strings.addNewMember)) {
            Employee employee = getSelected();
            tablePosition = employeeList.indexOf(employee);
            WorkGroupData newMember = new WorkGroupData();
            LocalDate currentDate = LocalDate.now();
            newMember.setGroupID(groupID);
            newMember.setEmployeeID(employee.getId());
            newMember.setEmployeeName(employee.getName());
            newMember.setAddedDate(currentDate);
            WorkGroupUpdate.addMember(newMember);
            employeeList.remove(tablePosition);
            if (employeeList.size() == 0) {
                btnAdd.setDisable(true);
            }
        }

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
            System.out.println("ERROR in method AddMembersController.btnBackAction error: "+e);
        }
    }

    public static void setGroupID(int id){
        groupID=id;
    }


    private boolean confirmAdd(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(message);
        Optional<ButtonType> result = alert.showAndWait();
        try {
            if (result.get() == ButtonType.OK) {
                alert.close();
                return true;
            } else {
                alert.close();
                return false;
            }
        }catch(NoSuchElementException e){
            alert.close();
            return false;
        }
    }
}
