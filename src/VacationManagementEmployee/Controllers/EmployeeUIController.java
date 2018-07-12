package VacationManagementEmployee.Controllers;


import Login.Models.Employee;
import Login.Models.EmployeeSearch;
import VacationManagementEmployee.Models.VacEmployee;
import VacationManagementEmployee.Models.VacEmployeeCancel;
import VacationManagementEmployee.Models.VacEmployeeGenerateReq;
import VacationManagementEmployee.Models.VacEmployeeSearch;
import VacationManagementSupervisor.Models.VacRequest;
import VacationManagementSupervisor.Models.VacRequestReadHistory;
import VacationManagementSupervisor.Models.VacRequestReadPending;
import VacationManagementSupervisor.Models.VacRequestSearch;
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
    @FXML private TableView<VacRequest> tableHistoricalRequest;
    @FXML private TableColumn columnRequestID;
    @FXML private TableColumn columnStartDate;
    @FXML private TableColumn columnEndDate;
    @FXML private TableColumn columnDays;
    @FXML private TableColumn columnStatus;

    @FXML private TableView<VacRequest> tableCancelRequest;
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
        LocalDate startDate = datePStartDate.getValue();
        LocalDate endDate = datePEndDate.getValue();
            if(startDate.isBefore(endDate)||startDate.isEqual(endDate)){
                if(startDate.isEqual(LocalDate.now())||startDate.isAfter(LocalDate.now())){
                    if(VacEmployeeGenerateReq.vacGenerateRequest(employeeID,startDate,endDate)){
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
            List<VacRequest> vacRequestList = tableCancelRequest.getSelectionModel().getSelectedItems();
            VacRequest vacRequest = vacRequestList.get(0);
            VacEmployeeCancel.cancelRequest(vacRequest.getPkIDRequest());
            loadTableCancelRequest();
        }
        buttonCancelVacation.setDisable(true);
    }

    @FXML
    private void onTabChangeEmployeeInfo(){
        try{
            loadTableHistorical();
            loadTableCancelRequest();
            loadEmployeeInfo();
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
            buttonCancelVacation.setDisable(true);
        } catch (Exception e){
        }
    }



    private void setLabelReqVacDays(){
        labelReqVacDays.setText(labelVacDays.getText());
    }


    private void loadTableHistorical(){
        ObservableList<VacRequest> vacRequests;
        vacRequests=FXCollections.observableArrayList(VacRequestReadHistory.getHistoryEmployee(employeeID));
        columnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        columnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableHistoricalRequest.setItems(vacRequests);
    }


    private void loadTableCancelRequest(){
        ObservableList<VacRequest> vacRequests;
        vacRequests=FXCollections.observableArrayList(VacRequestReadPending.getPendingRequestEmployee(employeeID));
        vacCancelColumnID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        vacCancelColumnStartD.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        vacCancelColumnEndD.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        vacCancelColumnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        tableCancelRequest.setItems(vacRequests);
    }


    private void loadEmployeeInfo(){
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
