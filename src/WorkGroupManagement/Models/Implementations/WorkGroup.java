package WorkGroupManagement.Models.Implementations;

import WorkGroupManagement.Models.Abstracts.AWorkGroup;

import java.time.LocalDate;

public class WorkGroup extends AWorkGroup {


    @Override
    public int getWorkGroupID() {
        return workGroupID;
    }

    @Override
    public void setWorkGroupID(int workGroupID) {
        super.workGroupID=workGroupID;
    }

    @Override
    public String getWorkGroupName() {
        return workGroupName;
    }

    @Override
    public void setWorkGroupName(String workGroupName) {
        super.workGroupName=workGroupName;
    }

    @Override
    public int getFkLeaderID() {
        return fkLeaderID;
    }

    @Override
    public void setFkLeaderID(int fkLeaderID) {
        super.fkLeaderID=fkLeaderID;
    }

    @Override
    public String getLeaderName() {
        return leaderName;
    }

    @Override
    public void setLeaderName(String leaderName) {
        super.leaderName=leaderName;
    }

    @Override
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(LocalDate createdDate) {
        super.createdDate=createdDate;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        super.status=status;
    }
}
