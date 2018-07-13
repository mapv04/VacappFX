package VacationManagementSupervisor.Models.Abstracts;



import java.util.List;

public interface IVacRequestReadPending {
     List<AVacRequest> getPendingRequestSupervisor(int supervisorID);
     List<AVacRequest> getPendingRequestEmployee(int emloyeeID);
}
