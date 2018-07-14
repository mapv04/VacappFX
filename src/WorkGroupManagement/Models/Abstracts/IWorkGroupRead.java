package WorkGroupManagement.Models.Abstracts;

import java.util.List;

public interface IWorkGroupRead {
    void getAllWorkgroups(List<AWorkGroup> workGroupsList);
    int getMembersCount(int workGroupID);
    void getWorkgroupData(List<AWorkGroupData> groupDataList, int groupID);
    String getGroupName(int groupID);
    boolean ifGroupExist(String name);
}
