package Login.Models.Implementations;

import Database.DatabaseConnection;
import Login.Models.Abstracts.IEmployeeRecover;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeRecover implements IEmployeeRecover {
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    @Override
    public void changePassword(String user, String password, int question, String response) {
        String sql="update usuario set password_user='"+password+"' where username = '"+user+"';";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("ERROR in sql statement on methos EmployeeUpdate.modifyEmployee error: "+e);
        }
    }
}
