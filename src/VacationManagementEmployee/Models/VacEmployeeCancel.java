package VacationManagementEmployee.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VacEmployeeCancel {
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;


    public static void cancelRequest(int vacRquestID){
        String sqlQuery1 = "DELETE FROM vac_request WHERE pk_id_request = ?;";
        try{
            pStatement = conn.prepareStatement(sqlQuery1);
            pStatement.setInt(1,vacRquestID);
            pStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacEmployeeCancel.cancelRequest error: " + e);
        }

    }
}
