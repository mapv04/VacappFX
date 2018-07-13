package VacationManagementSupervisor.Models.Abstracts;

import java.util.List;

public interface IVacRequestSearch {
    List<AVacRequest> searchAllRequests(int employeeID, int supervisorID);
    AVacRequest searchLatestRequest(int employeeID);
}
