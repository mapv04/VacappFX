package WorkGroupManagement.Models.Abstracts;

public interface IWorkGroupHandleStatus {
    void activateWorkGroup(int groupID);
    void desactivateWorkGroup(int groupID);
}
