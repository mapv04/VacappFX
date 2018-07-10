package VacationManagementSupervisor.Controllers;

import VacationManagementEmployee.Controllers.EmployeeUIController;
import VacationManagementSupervisor.Models.VacRequest;
import VacationManagementSupervisor.Models.VacRequestHandleStatus;
import VacationManagementSupervisor.Models.VacRequestRead;
import VacationManagementSupervisor.Models.VacRequestSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;


public class SupervisorUIController {

    @FXML private TableView<VacRequest> tablePendingRequest;
    @FXML private TableColumn pColumnRequestID;
    @FXML private TableColumn pColumnEmployeeID;
    @FXML private TableColumn pColumnName;
    @FXML private TableColumn pColumnStartDate;
    @FXML private TableColumn pColumnEndDate;
    @FXML private TableColumn pColumnDays;

    @FXML private TableView<VacRequest> tableHistoryRequest;
    @FXML private TableColumn hColumnRequestID;
    @FXML private TableColumn hColumnEmployeeID;
    @FXML private TableColumn hColumnName;
    @FXML private TableColumn hColumnStartDate;
    @FXML private TableColumn hColumnEndDate;
    @FXML private TableColumn hColumnDays;
    @FXML private TableColumn hColumnStatus;

    @FXML private Button buttonApprove;
    @FXML private Button buttonDeny;
    @FXML private Button employeeSceneButton;
    @FXML private TextField historyTextField;


    private ObservableList<VacRequest> vacRequests;
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to approve this employee's vacations?");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== buttonTypeYes){
            List<VacRequest> vacRequestList = tablePendingRequest.getSelectionModel().getSelectedItems();
            VacRequest vacRequest = vacRequestList.get(0);
            VacRequestHandleStatus.approveVacation(vacRequest.getPkIDRequest(),vacRequest.getFkIDUser(),vacRequest.getDaysRequested());
            loadTablePendingRequest();
            disableRequestButtons();
        }
    }


    @FXML
    private void denyRequest(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to deny this employee's vacations?");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()== buttonTypeYes){
            List<VacRequest> vacRequestList = tablePendingRequest.getSelectionModel().getSelectedItems();
            VacRequest vacRequest = vacRequestList.get(0);
            VacRequestHandleStatus.denyVacation(vacRequest.getPkIDRequest());
            loadTablePendingRequest();
            disableRequestButtons();
        }
    }


    @FXML
    private void requestsTabChange(){
        loadTablePendingRequest();
        disableRequestButtons();
    }


    @FXML
    private void historyTabChange(){
        loadTableHistoryRequest();
        try{
            historyTextField.setText("");
        }catch(NullPointerException e){ }
    }

    @FXML
    private void changeSceneTab(){
        EmployeeUIController.setEmployeeID(supervisorID);
        employeeSceneButton.setOnAction(a ->{
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/VacationManagementEmployee/Views/EmployeeUI.fxml"));
                Scene otherScene = new Scene(parent);
                Stage app_stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
                app_stage.setScene(otherScene);
                app_stage.show();

            } catch(Exception e) {
                System.out.println("cant load new window "+e);
            }
        });
        employeeSceneButton.fire();
    }


    @FXML
    private void searchHistoryByID(){
        String text = historyTextField.getText();
       if(text.isEmpty() || text.trim().isEmpty()) {
           loadTableHistoryRequest();
       } else{
           if(isNumeric(text)){
               loadTableHistoryRequestAr(VacRequestSearch.searchAllRequests(Integer.valueOf(text),supervisorID));
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



    private void loadTableHistoryRequest(){
        disableRequestButtons();
        vacRequests=FXCollections.observableArrayList(VacRequestRead.getHistorySupervisor(supervisorID));
        hColumnRequestID.setCellValueFactory(new PropertyValueFactory<>("pkIDRequest"));
        hColumnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("fkIDUser"));
        hColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        hColumnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        hColumnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        hColumnDays.setCellValueFactory(new PropertyValueFactory<>("daysRequested"));
        hColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableHistoryRequest.setItems(vacRequests);
    }


    private void loadTableHistoryRequestAr(List<VacRequest> vacHistoryList){
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
        vacRequests=FXCollections.observableArrayList(VacRequestRead.getPendingRequestSupervisor(supervisorID));
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
