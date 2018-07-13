package VacationManagementSupervisor.Models.Abstracts;

import java.time.LocalDate;

public abstract class AVacRequest {
    protected int pkIDRequest;
    protected int fkIDUser;
    protected String name;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String status;
    protected int daysRequested;
    protected int SupervisorID;

    public abstract void setPkIDRequest(int pkIDRequest);
    public abstract  void setFkIDUser(int fkIDUser);
    public abstract void setName(String name);
    public abstract void setStatus(String status);
    public abstract void setSupervisorID(int supervisorID);
    public abstract void setStartDate(LocalDate startDate);
    public abstract void setEndDate(LocalDate endDatePassed);


    public abstract int getPkIDRequest();
    public abstract int getFkIDUser();
    public abstract int getSupervisorID();
    public abstract String getName();
    public abstract String getStatus();
    public abstract int getDaysRequested();
    public abstract String getStartDate();
    public abstract String getEndDate();


}
