package UserManagement.Models.Abstracts;

import java.util.List;

public interface IEmployeeRead {

    void getAllEmployees(List<AEmployee> employeeList);
    int getStatus(int employeeID);
    void getAllSupervisors(List<AEmployee> supervisors);
    void getJustEmployeesType(List<AEmployee> employees);


}
