package Login.Models.Abstracts;

import java.sql.SQLException;

public interface IEmployeeSearch {
    boolean searchEmployeeExists(AEmployee employee) throws SQLException;
    boolean searchEmployeeValidateAnswer(AEmployee employee) throws SQLException;
    int searchEmployeeUserName(String user);
    AEmployee searchEmployeeID(int employeeID);

}
