package WorkGroupManagement.Models.Abstracts;

import java.time.LocalDate;

public abstract class AWorkGroup {
    protected int workGroupID;
    protected String workGroupName;
    protected int fkLeaderID;
    protected String leaderName;
    protected LocalDate createdDate;
    protected int status;

    public abstract int getWorkGroupID();

    public abstract void setWorkGroupID(int workGroupID);

    public abstract String getWorkGroupName();

    public abstract void setWorkGroupName(String workGroupName);

    public abstract int getFkLeaderID();

    public abstract void setFkLeaderID(int fkLeaderID);

    public abstract String getLeaderName();

    public abstract void setLeaderName(String leaderName);

    public abstract LocalDate getCreatedDate();

    public abstract void setCreatedDate(LocalDate createdDate);

    public abstract int getStatus();

    public abstract void setStatus(int status);
}
