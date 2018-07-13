package VacationManagementEmployee.Models.Abstracts;

import VacationManagementSupervisor.Models.Abstracts.AVacRequest;
import java.util.List;

public interface IVacEmployeeReport {

    void createReportTable(List<AVacRequest> listRequests);
}
