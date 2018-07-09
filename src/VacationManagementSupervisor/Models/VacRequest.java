package VacationManagementSupervisor.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class VacRequest {
    private int pkIDRequest;
    private int fkIDUser;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private int daysRequested;
    private int SupervisorID;



    public void setPkIDRequest(int pkIDRequest) {
        this.pkIDRequest = pkIDRequest;
    }

    public void setFkIDUser(int fkIDUser) {
        this.fkIDUser = fkIDUser;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public void setSupervisorID(int supervisorID) {
        SupervisorID = supervisorID;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate.plusDays(1);
    }

    public void setEndDate(LocalDate endDatePassed) {
        this.endDate = endDatePassed.plusDays(1);
        if(ChronoUnit.DAYS.between(startDate, endDate)>0){
            this.daysRequested = (int)ChronoUnit.DAYS.between(startDate, endDate)+1;
        }
        else{
            this.daysRequested = (int)ChronoUnit.DAYS.between(startDate, endDate);
        }
    }

    public int getPkIDRequest() {
        return pkIDRequest;
    }

    public int getFkIDUser() {
        return fkIDUser;
    }

    public int getSupervisorID() {
        return SupervisorID;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(this.startDate);
    }

    public String getEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(this.endDate);
    }

    public String getStatus() {
        return status;
    }

    public int getDaysRequested() {
        return daysRequested;
    }
}
