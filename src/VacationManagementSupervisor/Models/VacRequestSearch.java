package VacationManagementSupervisor.Models;

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


    public static List<VacRequest> searchAllRequests(int employeeID, int supervisorID){
        List<VacRequest> listVacReq = new ArrayList<>();
        String sqlQuery = "SELECT * FROM vac_request vr" +
                          " INNER JOIN usuario u ON vr.fk_id_user = u.pk_id_user"+
                          " WHERE fk_id_user = ?" +
                          " AND supervisor_id = ? AND u.status_user = 1"+
                          " ORDER BY pk_id_request ASC;";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1, employeeID);
            pStatement.setInt(2,supervisorID);
            rs= pStatement.executeQuery();
            while(rs.next()){
                VacRequest vacReq = new VacRequest();
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setFkIDUser(rs.getInt(2));
                vacReq.setStartDate(rs.getDate(3).toLocalDate());
                vacReq.setEndDate(rs.getDate(4).toLocalDate());
                vacReq.setStatus(rs.getString(5));
                vacReq.setSupervisorID(rs.getInt(6));
                String name = rs.getString(8)+" "+ rs.getString(9);
                vacReq.setName(name);
                listVacReq.add(vacReq);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestSearch.searchAllRequests error: "+e);
        }
        return listVacReq;
    }



    public static VacRequest searchLatestRequest(int employeeID){
        VacRequest vacRequest =new VacRequest();
        String sqlQuery = "SELECT * FROM vac_request " +
                          "WHERE fk_id_user = ? " +
                          "ORDER BY pk_id_request DESC LIMIT 1";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1, employeeID);
            rs= pStatement.executeQuery();
            if(rs.next()){
                vacRequest.setPkIDRequest(rs.getInt(1));
                vacRequest.setStartDate(rs.getDate(3).toLocalDate());
                vacRequest.setEndDate(rs.getDate(4).toLocalDate());
                vacRequest.setStatus(rs.getString(5));
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestSearch.searchAllRequests error: "+e);
        }
        return vacRequest;
    }







}
