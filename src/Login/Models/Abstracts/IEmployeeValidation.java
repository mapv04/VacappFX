package Login.Models.Abstracts;

import Login.Models.Implementations.Employee;
import java.sql.SQLException;

public interface IEmployeeValidation {
    Employee employeeExist(String user);
    void blockUser(String user) throws SQLException;
}
