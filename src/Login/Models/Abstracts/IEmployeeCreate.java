package Login.Models.Abstracts;

import Login.Models.Implementations.Employee;

import java.sql.SQLException;

public interface IEmployeeCreate {
    void createEmployee(Employee employee) throws SQLException;
}
