package WorkGroupManagement.Models.Abstracts;

public interface IWorkGroupUpdate {
    void editGroup(AWorkGroup group);
    void createGroup(AWorkGroup group);
    void addMember(AWorkGroupData newMember);

}
