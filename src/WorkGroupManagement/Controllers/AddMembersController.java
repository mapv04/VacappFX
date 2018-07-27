package WorkGroupManagement.Controllers;

import UserManagement.Models.Abstracts.AEmployee;
import UserManagement.Models.Abstracts.IEmployeeRead;
import UserManagement.Models.Implementations.EmployeeRead;
import WorkGroupManagement.Models.Abstracts.IWorkGroupUpdate;
import WorkGroupManagement.Models.Implementations.WorkGroupData;
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

public class AddMembersController implements Initializable {
    //@FXML private TextField txtSearch;
    @FXML private TableView<AEmployee> table;
    @FXML private TableColumn columnID;
    @FXML private TableColumn columnName;
    @FXML private TableColumn columnEmail;
    @FXML private TableColumn columnStatus;
    @FXML private Button btnAdd;

    private static int groupID;
    private ObservableList<AEmployee> employeeList;
    private int tablePosition;
    Scene scene;
    Stage stage;
    Parent fxml;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();

        final ObservableList<AEmployee> employeeList= table.getSelectionModel().getSelectedItems();
        employeeList.addListener(tableSelector);
        btnAdd.setDisable(true);

       /* txtSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btnSearch.setDisable(false);
            }
        });*/

    }



    public void initializeTable(){
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeList = FXCollections.observableArrayList();
        IEmployeeRead employeeRead= new EmployeeRead();
        employeeRead.getJustEmployeesType(employeeList);
        table.setItems(employeeList);
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
            getSelected();
            btnAdd.setDisable(false);
        }
    };

    @FXML
    private void btnAddAction(){

        if(confirmAdd(Strings.addNewMember)) {
            AEmployee employee = getSelected();
            tablePosition = employeeList.indexOf(employee);
            WorkGroupData newMember = new WorkGroupData();
            LocalDate currentDate = LocalDate.now();
            newMember.setGroupID(groupID);
            newMember.setEmployeeID(employee.getId());
            newMember.setEmployeeName(employee.getName());
            newMember.setAddedDate(currentDate);
            IWorkGroupUpdate workGroupUpdate= new WorkGroupUpdate();
            workGroupUpdate.addMember(newMember);
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

    @FXML
    private void btnLogoutAction(ActionEvent event) {
        if (confirmAdd(Strings.logout)) {
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
