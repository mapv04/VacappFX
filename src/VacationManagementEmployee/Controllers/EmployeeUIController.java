package VacationManagementEmployee.Controllers;


import Login.Models.Employee;
import Login.Models.EmployeeSearch;
import VacationManagementEmployee.Models.Abstracts.*;
import VacationManagementEmployee.Models.Implemetations.VacEmployeeCancel;
import VacationManagementEmployee.Models.Implemetations.VacEmployeeGenerateReq;
import VacationManagementEmployee.Models.Implemetations.VacEmployeeReport;
import VacationManagementEmployee.Models.Implemetations.VacEmployeeSearch;
import VacationManagementSupervisor.Models.Implementations.VacRequestReadHistory;
import VacationManagementSupervisor.Models.Implementations.VacRequestReadPending;
import VacationManagementSupervisor.Models.Implementations.VacRequestSearch;
import VacationManagementSupervisor.Models.Abstracts.AVacRequest;
import VacationManagementSupervisor.Models.Abstracts.IVacRequestReadHistory;
import VacationManagementSupervisor.Models.Abstracts.IVacRequestReadPending;
import VacationManagementSupervisor.Models.Abstracts.IVacRequestSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class EmployeeUIController   {
    @FXML private TableView<AVacRequest> tableHistoricalRequest;
    @FXML private TableColumn columnRequestID;
    @FXML private TableColumn columnStartDate;
    @FXML private TableColumn columnEndDate;
    @FXML private TableColumn columnDays;
    @FXML private TableColumn columnStatus;

    @FXML private TableView<AVacRequest> tableCancelRequest;
    @FXML private TableColumn vacCancelColumnID;
    @FXML private TableColumn vacCancelColumnStartD;
    @FXML private TableColumn vacCancelColumnEndD;
    @FXML private TableColumn vacCancelColumnDays;

    @FXML private Label labelID;
    @FXML private Label labelUsername;
    @FXML private Label labelName;
    @FXML private Label labelEmail;
    @FXML private Label labelHiredDate;
    @FXML private Label labelVacDays;
    @FXML private Label labelReqID;
    @FXML private Label labelReqStatus;
    @FXML private Label labelReqVacDays;

    @FXML private Button buttonRequestVac;
    @FXML private Button buttonCancelVacation;
    @FXML private DatePicker datePStartDate;
    @FXML private DatePicker datePEndDate;

    private ButtonType buttonTypeYes = new ButtonType("Yes");
    private ButtonType buttonTypeNo = new ButtonType("No");
    private static int employeeID;


    public void initialize() {
        loadTableHistorical();
        loadEmployeeInfo();
        setLabelReqVacDays();
        loadTableCancelRequest();
        buttonRequestVac.setDisable(true);
        buttonCancelVacation.setDisable(true);
    }

    @FXML
    private void generateVacRequest(){
        IVacEmployeeGenerateReq vacEmployeeGenerateReq = new VacEmployeeGenerateReq();
        LocalDate startDate = datePStartDate.getValue();
        LocalDate endDate = datePEndDate.getValue();
            if(startDate.isBefore(endDate)||startDate.isEqual(endDate)){
                if(startDate.isEqual(LocalDate.now())||startDate.isAfter(LocalDate.now())){
                    if(vacEmployeeGenerateReq.vacGenerateRequest(employeeID,startDate,endDate)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Vacation Request");
                        alert.setHeaderText("Your vacation request was submitted for approval");
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error vacation request");
                        alert.setHeaderText("You selected more vacation days than the ones available to you");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error vacation request");
                    alert.setHeaderText("Dates can't be before today");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error vacation request");
                alert.setHeaderText("The end date needs to be greater or equal than start date");
                alert.show();
            }
        datePStartDate.setValue(null);
        datePEndDate.setValue(null);
        buttonRequestVac.setDisable(true);
    }



    @FXML
    private void enableVacRequestButton(){
        if(datePEndDate.getValue()!=null&&datePStartDate!=null){
            buttonRequestVac.setDisable(false);
        }
    }


    @FXML
    private void enableCancelButton(){
        buttonCancelVacation.setDisable(false);
    }

    @FXML
    private void cancelVacationRequest(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to delete this vacation request?");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()== buttonTypeYes){
            IVacEmployeeCancel vacEmployeeCancel = new VacEmployeeCancel();
            List<AVacRequest> vacRequestList = tableCancelRequest.getSelectionModel().getSelectedItems();
            AVacRequest vacRequest = vacRequestList.get(0);
            vacEmployeeCancel.cancelRequest(vacRequest.getPkIDRequest());
            loadTableCancelRequest();
        }
        buttonCancelVacation.setDisable(true);
    }

    @FXML
    private void generateReport() {
        IVacEmployeeReport vacEmployeeReport = new VacEmployeeReport();
        List<AVacRequest> vacRequestList = tableHistoricalRequest.getItems();
        vacEmployeeReport.createReportTable(vacRequestList);
    }

    @FXML
    private void onTabChangeEmployeeInfo(){
        try{
            loadTableHistorical();
            loadTableCancelRequest();
            loadEmployeeInfo();
            setLabelReqVacDays();
            buttonCancelVacation.setDisable(true);
        } catch (Exception e){

        }
    }

    @FXML
    private void onTabChangeVacationReport(){
        try{
            loadTableHistorical();
            loadTableCancelRequest();
            loadEmployeeInfo();
            setLabelReqVacDays();
            buttonCancelVacation.setDisable(true);
        } catch (Exception e){
        }
    }

    @FXML
    private void onTabChangeVacationCancel(){
        try{
            loadTableHistorical();
            loadTableCancelRequest();
            loadEmployeeInfo();
            setLabelReqVacDays();
            buttonCancelVacation.setDisable(true);
        } catch (Exception e){
        }
    }



    private void setLabelReqVacDays(){
        labelReqVacDays.setText(labelVacDays.getText());
    }


    private void loadTableHistorical(){
        IVacRequestReadHistory vacRequestReadHistory = new VacRequestReadHistory();
        ObservableList<AVacRequest> vacRequests;
        vacRequests=FXCollections.observableArrayList(vacRequestReadHistory.getHistoryEmployee(employeeID));
        columnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        columnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableHistoricalRequest.setItems(vacRequests);
    }


    private void loadTableCancelRequest(){
        IVacRequestReadPending vacRequestReadPending = new VacRequestReadPending();
        ObservableList<AVacRequest> vacRequests;
        vacRequests=FXCollections.observableArrayList(vacRequestReadPending.getPendingRequestEmployee(employeeID));
        vacCancelColumnID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        vacCancelColumnStartD.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        vacCancelColumnEndD.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        vacCancelColumnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        tableCancelRequest.setItems(vacRequests);
    }


    private void loadEmployeeInfo(){
        IVacRequestSearch vacRequestSearch = new VacRequestSearch();
        IVacEmployeeSearch vacEmployeeSearch = new VacEmployeeSearch();
        Employee employee = EmployeeSearch.searchEmployeeID(employeeID);
        AVacEmployee vacEmployee = vacEmployeeSearch.searchVacEmployeeData(employeeID);
        AVacRequest vacRequest = vacRequestSearch.searchLatestRequest(employeeID);

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
            labelReqID.setText("None");
            labelReqStatus.setText("None");
        }
    }


    public static void setEmployeeID(int ID){
        employeeID = ID;
    }
    
}
