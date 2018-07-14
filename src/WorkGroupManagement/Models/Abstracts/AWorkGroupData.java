package WorkGroupManagement.Models.Abstracts;

import java.time.LocalDate;

public abstract class AWorkGroupData {
    protected int groupID;
    protected int employeeID;
    protected String employeeName;
    protected LocalDate addedDate;
    protected int employeeStatus;

    public abstract int getGroupID();

    public abstract void setGroupID(int groupID);

    public abstract int getEmployeeID();

    public abstract void setEmployeeID(int employeeID);

    public abstract String getEmployeeName();

    public abstract void setEmployeeName(String employeeName);

    public abstract LocalDate getAddedDate();

    public abstract void setAddedDate(LocalDate addedDate);

    public abstract int getEmployeeStatus();

    public abstract void setEmployeeStatus(int employeeStatus);
}
