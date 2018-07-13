package VacationManagementSupervisor.Models.Abstracts;

public interface IVacRequestHandleStatus {
    void approveVacation(int requestID, int employeeID , int days);
    void denyVacation(int requestID);
}
