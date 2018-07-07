package VacationManagement.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VacRequestSearch {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;


    public static List<VacRequest> searchAllByEmployeeID(int employeeID, int supervisorID){
        List<VacRequest> listVacReq = new ArrayList<>();
        String sqlQuery = "SELECT * FROM vac_request WHERE fk_id_user = ? AND supervisor_id = ?;";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1, employeeID);
            pStatement.setInt(2,supervisorID);
            rs= pStatement.executeQuery();
            while(rs.next()){
                VacRequest vacReq = new VacRequest();
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setFkIDUser(rs.getInt(2));
                vacReq.setName(rs.getString(3));
                vacReq.setStartDate(rs.getDate(4).toLocalDate());
                vacReq.setEndDate(rs.getDate(5).toLocalDate());
                vacReq.setStatus(rs.getString(6));
                vacReq.setSupervisorID(rs.getInt(7));
                listVacReq.add(vacReq);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestSearch.searchAllByEmployeeID error: "+e);
        }
        return listVacReq;
    }



    public static VacRequest searchLatestByEmployeeID(int employeeID){
        VacRequest vacRequest = new VacRequest();
        String sqlQuery = "SELECT * FROM vac_request WHERE fk_id_user = ? ORDER BY pk_id_request DESC LIMIT 1";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1, employeeID);
            rs= pStatement.executeQuery();
            if(rs.next()){
                vacRequest.setPkIDRequest(rs.getInt(1));
                vacRequest.setName(rs.getString(3));
                vacRequest.setStartDate(rs.getDate(4).toLocalDate());
                vacRequest.setEndDate(rs.getDate(5).toLocalDate());
                vacRequest.setStatus(rs.getString(6));
            }else {
                System.out.println("ERROR VacRequestSearch.searchAllByEmployeeID the query doesn't have any results");
            }

        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestSearch.searchAllByEmployeeID error: "+e);
        }
        return vacRequest;
    }



}
