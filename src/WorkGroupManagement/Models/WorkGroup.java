package WorkGroupManagement.Models;

import java.time.LocalDate;

public class WorkGroup {

    private int workGroupID;
    private String workGroupName;
    private int fkLeaderID;
    private String leaderName;
    private LocalDate createdDate;
    private int status;

    public int getWorkGroupID() {
        return workGroupID;
    }

    public void setWorkGroupID(int workGroupID) {
        this.workGroupID = workGroupID;
    }

    public String getWorkGroupName() {
        return workGroupName;
    }

    public void setWorkGroupName(String workGroupName) {
        this.workGroupName = workGroupName;
    }

    public int getFkLeaderID() {
        return fkLeaderID;
    }

    public void setFkLeaderID(int fkLeaderID) {
        this.fkLeaderID = fkLeaderID;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
