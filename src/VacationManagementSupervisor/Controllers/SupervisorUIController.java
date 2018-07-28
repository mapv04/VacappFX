package VacationManagementSupervisor.Controllers;

import VacationManagementEmployee.Controllers.EmployeeUIController;
import VacationManagementSupervisor.Models.Abstracts.*;
import VacationManagementSupervisor.Models.Implementations.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class SupervisorUIController {

    @FXML private TableView<AVacRequest> tablePendingRequest;
    @FXML private TableColumn pColumnRequestID;
    @FXML private TableColumn pColumnEmployeeID;
    @FXML private TableColumn pColumnName;
    @FXML private TableColumn pColumnStartDate;
    @FXML private TableColumn pColumnEndDate;
    @FXML private TableColumn pColumnDays;

    @FXML private TableView<AVacRequest> tableHistoryRequest;
    @FXML private TableColumn hColumnRequestID;
    @FXML private TableColumn hColumnEmployeeID;
    @FXML private TableColumn hColumnName;
    @FXML private TableColumn hColumnStartDate;
    @FXML private TableColumn hColumnEndDate;
    @FXML private TableColumn hColumnDays;
    @FXML private TableColumn hColumnStatus;

    @FXML private Button buttonApprove;
    @FXML private Button buttonDeny;
    @FXML private TextField historyTextField;

    private ButtonType buttonTypeYes = new ButtonType("Yes");
    private ButtonType buttonTypeNo = new ButtonType("No");
    private static int supervisorID;


    public void initialize() {
        loadTablePendingRequest();
        loadTableHistoryRequest();
        disableRequestButtons();
    }

    @FXML
    private void approveRequest(){
        IVacRequestHandleStatus vacRequestHandleStatus = new VacRequestHandleStatus();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to approve this employee's vacations?");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== buttonTypeYes){
            List<AVacRequest> vacRequestList = tablePendingRequest.getSelectionModel().getSelectedItems();
            AVacRequest vacRequest = vacRequestList.get(0);
            vacRequestHandleStatus.approveVacation(vacRequest.getPkIDRequest(),vacRequest.getFkIDUser(),vacRequest.getDaysRequested());
            loadTablePendingRequest();
            disableRequestButtons();
        }
    }


    @FXML
    private void denyRequest(){
        IVacRequestHandleStatus vacRequestHandleStatus = new VacRequestHandleStatus();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to deny this employee's vacations?");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()== buttonTypeYes){
            List<AVacRequest> vacRequestList = tablePendingRequest.getSelectionModel().getSelectedItems();
            AVacRequest vacRequest = vacRequestList.get(0);
            vacRequestHandleStatus.denyVacation(vacRequest.getPkIDRequest());
            loadTablePendingRequest();
            disableRequestButtons();
        }
    }

    @FXML
    private void onTabChangeRequest(){
        loadTablePendingRequest();
        disableRequestButtons();
    }


    @FXML
    private void onTabChangeReport(){
        loadTableHistoryRequest();
        try{
            historyTextField.setText("");
        }catch(Exception e){ }
    }



    @FXML
    private void onEmployeeUIClick(ActionEvent event) throws  IOException {
        Scene scene;
        Parent fxml;
        Stage stage;
        EmployeeUIController.setEmployeeID(supervisorID);
        EmployeeUIController.setIsSupervisor(true);
        fxml = FXMLLoader.load(getClass().getResource("/VacationManagementEmployee/Views/EmployeeUI.fxml"));
        scene = new Scene(fxml);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onLoginUIClick(ActionEvent event) throws  IOException {
            Scene scene;
            Parent fxml;
            Stage stage;
            fxml = FXMLLoader.load(getClass().getResource("/Login/Views/LoginUI.fxml"));
            scene = new Scene(fxml);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
    }


    @FXML
    private void searchHistoryByID(){
        IVacRequestFactory  requestFactory = new VacRequestFactory();
        IVacRequestSearch vacRequestSearch = new VacRequestSearch(requestFactory.getVacRequestList(),
                                                                requestFactory.getVacRequest(),
                                                                requestFactory);
        String text = historyTextField.getText();
       if(text.isEmpty() || text.trim().isEmpty()) {
           loadTableHistoryRequest();
       } else{
           if(isNumeric(text)){
               loadTableHistoryRequest(vacRequestSearch.searchAllRequests(Integer.valueOf(text),supervisorID));
           }
       }
    }

    @FXML
    private void enableRequestButtons(){
        if(!tablePendingRequest.getSelectionModel().isEmpty()){
            buttonApprove.setDisable(false);
            buttonDeny.setDisable(false);
        }
    }

    @FXML
    private void generateReport(){
        IVacRequestFactory  requestFactory = new VacRequestFactory();
        IVacRequestReport vacRequestReport = new VacRequestReport(requestFactory.getDocumentO(),requestFactory.getTableO(),
                                                                  requestFactory.getParagraph1(),requestFactory.getParagraph2(),
                                                                  requestFactory.getFileOutputStream(),requestFactory.getFileO());
        List<AVacRequest> vacRequestList = tableHistoryRequest.getItems();
        vacRequestReport.createReportTable(vacRequestList);
    }



    private void loadTableHistoryRequest(){
        ObservableList<AVacRequest> vacRequests;
        IVacRequestFactory  requestFactory = new VacRequestFactory();
        IVacRequestReadHistory vacRequestReadHistory = new VacRequestReadHistory(requestFactory.getVacRequestList(),
                                                                                requestFactory.getVacRequest(),
                                                                                requestFactory);
        disableRequestButtons();
        vacRequests=FXCollections.observableArrayList(vacRequestReadHistory.getHistorySupervisor(supervisorID));
        hColumnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        hColumnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("fkIDUser"));
        hColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        hColumnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        hColumnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        hColumnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        hColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableHistoryRequest.setItems(vacRequests);
    }


    private void loadTableHistoryRequest(List<AVacRequest> vacHistoryList){
        ObservableList<AVacRequest> vacRequests;
        vacRequests=FXCollections.observableArrayList(vacHistoryList);
        hColumnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        hColumnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("fkIDUser"));
        hColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        hColumnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        hColumnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        hColumnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        hColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableHistoryRequest.setItems(vacRequests);
    }


    private void loadTablePendingRequest(){
        ObservableList<AVacRequest> vacRequests;
        IVacRequestFactory  requestFactory = new VacRequestFactory();
        IVacRequestReadPending vacRequestReadPending = new VacRequestReadPending(requestFactory.getVacRequestList(),
                                                                                requestFactory.getVacRequest(),
                                                                                requestFactory);
        vacRequests=FXCollections.observableArrayList(vacRequestReadPending.getPendingRequestSupervisor(supervisorID));
        pColumnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        pColumnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("fkIDUser"));
        pColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        pColumnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        pColumnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        pColumnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        tablePendingRequest.setItems(vacRequests);
    }



    private void disableRequestButtons(){
            buttonApprove.setDisable(true);
            buttonDeny.setDisable(true);
    }


    private static boolean isNumeric(String str) {
        try {
            Integer i = Integer.parseInt(str);
            return true;
        } catch(NumberFormatException nfe) {
            return false;
        }

    }


    public static void setSupervisorID(int ID){
        supervisorID = ID;
    }



}
