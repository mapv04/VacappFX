package VacationManagementSupervisor.Models.Implementations;

import VacationManagementSupervisor.Models.Abstracts.AVacRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class VacRequest extends AVacRequest {

    @Override
    public void setPkIDRequest(int pkIDRequest) {
        super.pkIDRequest = pkIDRequest;
    }
    @Override
    public void setFkIDUser(int fkIDUser) {
        super.fkIDUser = fkIDUser;
    }
    @Override
    public void setName(String name) {
        super.name = name;
    }
    @Override
    public void setStatus(String status) {
        super.status = status;
    }
    @Override
    public void setSupervisorID(int supervisorID) { super.SupervisorID = supervisorID; }
    @Override
    public void setStartDate(LocalDate startDate) {
        super.startDate = startDate.plusDays(1);
    }
    @Override
    public void setEndDate(LocalDate endDatePassed) {
        this.endDate = endDatePassed.plusDays(1);
        if(ChronoUnit.DAYS.between(startDate, endDate)>0){
            super.daysRequested = (int)ChronoUnit.DAYS.between(startDate, endDate)+1;
        }
        else{
            super.daysRequested = 1;
        }
    }

    @Override
    public int getPkIDRequest() {
        return pkIDRequest;
    }
    @Override
    public int getFkIDUser() {
        return fkIDUser;
    }
    @Override
    public int getSupervisorID() {
        return SupervisorID;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getStatus() {
        return status;
    }
    @Override
    public int getDaysRequested() {
        return daysRequested;
    }
    @Override
    public String getStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(startDate);
    }
    @Override
    public String getEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(endDate);
    }
}
