package VacationManagementSupervisor.Models.Abstracts;

import java.util.List;

public interface IVacRequestReadHistory {
    List<AVacRequest> getHistorySupervisor(int supervisorID);
    List<AVacRequest> getHistoryEmployee(int employeeID);
}
