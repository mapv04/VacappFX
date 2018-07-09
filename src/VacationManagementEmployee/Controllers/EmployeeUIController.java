package VacationManagementEmployee.Controllers;


import Login.Models.Employee;
import Login.Models.EmployeeSearch;
import VacationManagementEmployee.Models.VacEmployee;
import VacationManagementEmployee.Models.VacEmployeeSearch;
import VacationManagementSupervisor.Models.VacRequest;
import VacationManagementSupervisor.Models.VacRequestRead;
import VacationManagementSupervisor.Models.VacRequestSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;


public class EmployeeUIController   {
    @FXML private TableView<VacRequest> tableHistoricalRequest;
    @FXML private TableColumn columnRequestID;
    @FXML private TableColumn columnStartDate;
    @FXML private TableColumn columnEndtDate;
    @FXML private TableColumn columnDays;
    @FXML private TableColumn columnStatus;

    @FXML private Label labelID;
    @FXML private Label labelUsername;
    @FXML private Label labelName;
    @FXML private Label labelEmail;
    @FXML private Label labelHiredDate;
    @FXML private Label labelVacDays;
    @FXML private Label labelReqID;
    @FXML private Label labelReqStatus;


    @FXML private Button buttonRequestVac;
    @FXML private DatePicker datePStartDate;
    @FXML private DatePicker datePEndDate;

    private static int employeeID;


    public void initialize() {
        loadTableHistorical();
        setEmployeeInfo();
        buttonRequestVac.setDisable(true);
    }


    private void loadTableHistorical(){
        ObservableList<VacRequest> vacRequests;
        vacRequests=FXCollections.observableArrayList(VacRequestRead.getHistoryEmployee(employeeID));
        columnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndtDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        columnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableHistoricalRequest.setItems(vacRequests);
    }



    private void setEmployeeInfo(){
        Employee employee = EmployeeSearch.searchEmployeeID(employeeID);
        VacEmployee vacEmployee = VacEmployeeSearch.searchVacEmployeeData(employeeID);
        VacRequest vacRequest = VacRequestSearch.searchLatestRequest(employeeID);

        labelID.setText(String.valueOf(employee.getId()));
        labelUsername.setText(employee.getUsername());
        labelName.setText(employee.getName()+" "+employee.getLastName());
        labelEmail.setText(employee.getEmail());

        labelHiredDate.setText(vacEmployee.getHiredDate());
        labelVacDays.setText(String.valueOf(vacEmployee.getVacDaysAvailable()));

        if(vacRequest.getPkIDRequest()!=0){
            if(vacRequest.getStatus().equals("Pending")){
                labelReqStatus.setTextFill(Color.DARKORANGE);
            }else if(vacRequest.getStatus().equals("Approved")){
                labelReqStatus.setTextFill(Color.LIMEGREEN);
            }else {
                labelReqStatus.setTextFill(Color.RED);
            }
            labelReqID.setText(String.valueOf(vacRequest.getPkIDRequest()));
            labelReqStatus.setText(vacRequest.getStatus());
        }
        else {
            labelReqID.setText(" ");
            labelReqStatus.setText("  ");
        }
    }



    public static void setEmployeeID(int ID){
        employeeID = ID;
    }
    
}
