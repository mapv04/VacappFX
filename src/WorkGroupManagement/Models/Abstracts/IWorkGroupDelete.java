package WorkGroupManagement.Models.Abstracts;

public interface IWorkGroupDelete {
    void deleteGroup(int groupID);
    void deleteFromGroup(int employeeID);
}
