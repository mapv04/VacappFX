package UserManagement.Models.Abstracts;

public interface IEmployeeHandleStatus {
    void activateUser(int employeeID);

    void desactivateUser(int employeeID);

}
