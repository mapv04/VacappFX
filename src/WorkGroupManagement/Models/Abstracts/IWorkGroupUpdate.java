package WorkGroupManagement.Models.Abstracts;

public interface IWorkGroupUpdate {
    void editGroup(AWorkGroup group);
    void createGroup(AWorkGroup group);
    void addMember(AWorkGroupData newMember);
    void changeLeader(int id, String name, int groupID);

}
