package VacationManagementEmployee.Models.Abstracts;

import java.time.LocalDate;

public abstract class AVacEmployee {
    protected int pkID;
    protected int employeeID;
    protected LocalDate hiredDate;
    protected int vacDaysAvailable;

    public abstract void setPkID(int pkID);
    public abstract void setEmployeeID(int employeeID);
    public abstract void setHiredDate(LocalDate hiredDate);
    public abstract void setVacDaysAvailable(int vacDaysAvailable);

    public abstract int getPkID();
    public abstract int getEmployeeID();
    public abstract String getHiredDate() ;
    public abstract int getVacDaysAvailable();
}
