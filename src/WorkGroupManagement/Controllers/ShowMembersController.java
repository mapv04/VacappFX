package WorkGroupManagement.Controllers;

import Interfaces.Confirmations;
import Interfaces.Tables;
import WorkGroupManagement.Models.WorkGroupData;
import WorkGroupManagement.Models.WorkGroupDelete;
import WorkGroupManagement.Models.WorkGroupRead;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class ShowMembersController implements Initializable,Tables, Confirmations {

    @FXML private Label labelName;
    @FXML private TableView<WorkGroupData> table;
    @FXML private TableColumn columnID;
    @FXML private TableColumn columnName;
    @FXML private TableColumn columnStatus;
    @FXML private TableColumn columnDateAdded;
    @FXML private Button btnDelete;

    private ObservableList<WorkGroupData> groupDataList;
    private int tablePosition;
    public static int groupID;
    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelName.setText(WorkGroupRead.getGroupName(groupID));

        initializeTable();
        btnDelete.setDisable(true);
        final ObservableList<WorkGroupData> groupList= table.getSelectionModel().getSelectedItems();
        groupList.addListener(tableSelector);
    }


    @Override
    public void initializeTable() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("employeeStatus"));
        columnDateAdded.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        groupDataList = FXCollections.observableArrayList();
        WorkGroupRead.getWorkgroupData(groupDataList,this.groupID);
        table.setItems(groupDataList);
    }


    @Override
    public WorkGroupData getSelected() {
        if (table != null) {
            List<WorkGroupData> groupList = table.getSelectionModel().getSelectedItems();
            if (groupList.size() == 1) {
                final WorkGroupData groupSelected = groupList.get(0);
                btnDelete.setDisable(false);
                return groupSelected;
            }
        }
        return null;
    }

    private final ListChangeListener<WorkGroupData> tableSelector = new ListChangeListener<WorkGroupData>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends WorkGroupData> c) {
            getSelected();
        }
    };

    @FXML
    private void btnDeleteFromGroupAction(){
        if(confirmChanges(deleteEmployeeFromGroup)) {
            WorkGroupData group = getSelected();
            tablePosition=groupDataList.indexOf(group);
            WorkGroupDelete.deleteFromGroup(group.getEmployeeID());
            groupDataList.remove(tablePosition);
            btnDelete.setDisable(true);
        }
    }


    public static void getStageID(int id){
        groupID=id;
    }

    @Override
    public boolean confirmChanges(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(message);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            return true;
        }
        return false;
    }
}
