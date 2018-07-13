package VacationManagementEmployee.Models.Implemetations;

import Database.DatabaseConnection;
import VacationManagementEmployee.Models.Abstracts.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VacEmployeeCancel implements IVacEmployeeCancel {
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;

    @Override
      public void cancelRequest(int vacRequestID) {
        String sqlQuery1 = "DELETE FROM vac_request WHERE pk_id_request = ?;";
        try{
            pStatement = conn.prepareStatement(sqlQuery1);
            pStatement.setInt(1,vacRequestID);
            pStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacEmployeeCancel.cancelRequest error: " + e);
        }

    }



}
