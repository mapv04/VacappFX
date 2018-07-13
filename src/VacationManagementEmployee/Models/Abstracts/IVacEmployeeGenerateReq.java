package VacationManagementEmployee.Models.Abstracts;

import java.time.LocalDate;

public interface IVacEmployeeGenerateReq {
    boolean vacGenerateRequest(int employeeID, LocalDate start_date, LocalDate end_date);
}
