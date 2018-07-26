package WorkGroupManagement.Controllers;

import WorkGroupManagement.Models.Abstracts.AWorkGroupData;
import WorkGroupManagement.Models.Abstracts.IWorkGroupDelete;
import WorkGroupManagement.Models.Abstracts.IWorkGroupRead;
import WorkGroupManagement.Models.Implementations.WorkGroupDelete;
import WorkGroupManagement.Models.Implementations.WorkGroupRead;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class ShowMembersController implements Initializable {

    @FXML private Label labelName;
    @FXML private TableView<AWorkGroupData> table;
    @FXML private TableColumn columnID;
    @FXML private TableColumn columnName;
    @FXML private TableColumn columnStatus;
    @FXML private TableColumn columnDateAdded;
    @FXML private Button btnDelete;

    private ObservableList<AWorkGroupData> groupDataList;
    private int tablePosition;
    public static int groupID;
    Scene scene;
    Parent fxml;
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IWorkGroupRead workGroupRead= new WorkGroupRead();
        labelName.setText(workGroupRead.getGroupName(groupID));

        initializeTable();
        btnDelete.setDisable(true);
        final ObservableList<AWorkGroupData> groupList= table.getSelectionModel().getSelectedItems();
        groupList.addListener(tableSelector);
    }



    public void initializeTable() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("employeeStatus"));
        columnDateAdded.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        groupDataList = FXCollections.observableArrayList();
        IWorkGroupRead workGroupRead = new WorkGroupRead();
        workGroupRead.getWorkgroupData(groupDataList,this.groupID);
        table.setItems(groupDataList);
    }


    public AWorkGroupData getSelected() {
        if (table != null) {
            List<AWorkGroupData> groupList = table.getSelectionModel().getSelectedItems();
            if (groupList.size() == 1) {
                final AWorkGroupData groupSelected = groupList.get(0);
                return groupSelected;
            }
        }
        return null;
    }

    private final ListChangeListener<AWorkGroupData> tableSelector = new ListChangeListener<AWorkGroupData>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends AWorkGroupData> c) {
            getSelected();
            btnDelete.setDisable(false);
        }
    };

    @FXML
    private void btnDeleteFromGroupAction(){
        if(confirmChanges(Strings.deleteEmployeeFromGroup)) {
            AWorkGroupData group = getSelected();
            tablePosition=groupDataList.indexOf(group);
            IWorkGroupDelete workGroupDelete= new WorkGroupDelete();
            workGroupDelete.deleteFromGroup(group.getEmployeeID());
            groupDataList.remove(tablePosition);
            if(groupDataList.size()==0){
                btnDelete.setDisable(true);
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
            System.out.println("ERROR in method ShowMembersController.btnBackAction error: "+e);
        }
    }


    public static void getStageID(int id){
        groupID=id;
    }

    public boolean confirmChanges(String message) {
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

}
