package WorkGroupManagement.Models.Implementations;

import WorkGroupManagement.Models.Abstracts.AWorkGroupData;

import java.time.LocalDate;

public class WorkGroupData extends AWorkGroupData {

    @Override
    public int getGroupID() {
        return  groupID;
    }

    @Override
    public void setGroupID(int groupID) {
        super.groupID=groupID;
    }

    @Override
    public int getEmployeeID() {
        return employeeID;
    }

    @Override
    public void setEmployeeID(int employeeID) {
        super.employeeID=employeeID;
    }

    @Override
    public String getEmployeeName() {
        return employeeName;
    }

    @Override
    public void setEmployeeName(String employeeName) {
        super.employeeName=employeeName;
    }

    @Override
    public LocalDate getAddedDate() {
        return addedDate;
    }

    @Override
    public void setAddedDate(LocalDate addedDate) {
        super.addedDate=addedDate;
    }

    @Override
    public int getEmployeeStatus() {
        return employeeStatus;
    }

    @Override
    public void setEmployeeStatus(int employeeStatus) {
        super.employeeStatus=employeeStatus;
    }
}
