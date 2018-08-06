package Login.Models.Abstracts;

import java.sql.SQLException;

public interface IEmployeeValidation {
    AEmployee employeeExist(String user);
    void blockUser(String user) throws SQLException;
}
