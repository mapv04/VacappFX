package Login.Models.Abstracts;

import Login.Models.Implementations.Employee;

import java.sql.SQLException;

public interface IEmployeeSearch {
    boolean searchEmployeeExists(Employee employee) throws SQLException;
    int searchEmployeeUserName(String user);
    Employee searchEmployeeID(int employeeID);

}
