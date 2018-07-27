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
    public Void changePassword(String user, String password, int question, String response) {
<<<<<<< HEAD
        System.out.println("dato: "+ user);
        System.out.println("dato: "+ password);
        System.out.println("dato: "+ String.valueOf(question));
        System.out.println("dato: "+ response);

        String sql="update usuario set password_user '"+password+"' where name_user = '"+user+"';";

=======
        String sql="update usuario set password_user='"+password+"' where name_user = '"+user+"';";
>>>>>>> 78db8fb2bf8dabfea14b7292d66885e218009d29
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("ERROR in sql statement on methos EmployeeUpdate.modifyEmployee error: "+e);
        }
        return null;
    }
}
