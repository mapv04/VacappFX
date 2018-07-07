package VacationManagement.Controllers;

import VacationManagement.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class EmployeeUIController   {

    @FXML private TableView<VacRequest> tableHistoricalRequest;
    @FXML private TableColumn columnRequestID;
    @FXML private TableColumn columnEmployeeName;
    @FXML private TableColumn columnStartDate;
    @FXML private TableColumn columnEndtDate;
    @FXML private TableColumn columnDays;
    @FXML private TableColumn columnStatus;

    @FXML private Button buttonRequestVac;
    @FXML private Label reqLabelDaysAvailable;

    @FXML private DatePicker datePStartDate;
    @FXML private DatePicker datePEndDate;

    private static int employeeID;



    public void initialize() {
        loadTableHistoricalByID();
        buttonRequestVac.setDisable(true);
    }


    @FXML
    private void loadTableHistoricalByID(){
        ObservableList<VacRequest> vacRequests;
        vacRequests=FXCollections.observableArrayList(VacRequestRead.getRequestHistory(employeeID));
        columnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        columnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndtDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        columnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableHistoricalRequest.setItems(vacRequests);
    }


    @FXML
    private void setReqLabelDaysAvailable(){

        reqLabelDaysAvailable.setText("hello");
    }




    public static void setEmployeeID(int ID){
        employeeID = ID;
    }
    
}
