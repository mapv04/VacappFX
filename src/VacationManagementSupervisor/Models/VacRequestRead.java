package VacationManagementSupervisor.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VacRequestRead {

    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;


    public static List<VacRequest> getPendingRequestSupervisor(int supervisorID){
        List<VacRequest> listVacReq = new ArrayList<>();
        rs = null;
        String sqlQuery = "SELECT * FROM vac_request vr" +
                          " INNER JOIN usuario u ON vr.fk_id_user = u.pk_id_user"+
                          " WHERE vr.supervisor_id = ?" +
                          " AND vt.status = 'Pending'  AND u.status_user = 1" +
                          " ORDER BY pk_id_request DESC;";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1,supervisorID);
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
            System.out.println("ERROR in sql statement method VacRequestRead.getPendingRequestSupervisor error: "+e);
        }

        return listVacReq;
    }


    public static List<VacRequest> getPendingRequestEmployee(int emloyeeID){
        List<VacRequest> listVacReq = new ArrayList<>();
        rs = null;
        String sqlQuery = "SELECT * FROM vac_request vr" +
                " WHERE  fk_id_user  = ?  AND status = 'Pending'  " +
                " ORDER BY pk_id_request DESC;";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1,emloyeeID);
            rs= pStatement.executeQuery();
            while(rs.next()){
                VacRequest vacReq = new VacRequest();
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setFkIDUser(rs.getInt(2));
                vacReq.setStartDate(rs.getDate(3).toLocalDate());
                vacReq.setEndDate(rs.getDate(4).toLocalDate());
                vacReq.setStatus(rs.getString(5));
                vacReq.setSupervisorID(rs.getInt(6));
                listVacReq.add(vacReq);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestRead.getPendingRequestSupervisor error: "+e);
        }

        return listVacReq;
    }




    public static List<VacRequest> getHistorySupervisor(int supervisorID){
        List<VacRequest> listVacReq = new ArrayList<>();
        rs = null;
        String sqlQuery = "SELECT * FROM vac_request vr" +
                          " INNER JOIN usuario u ON vr.fk_id_user = u.pk_id_user"+
                          " WHERE supervisor_id = ? AND u.status_user = 1" +
                          " ORDER BY fk_id_user ASC;";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1,supervisorID);
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
            System.out.println("ERROR in sql statement method VacRequestRead.getHistorySupervisor error: "+e);
        }

        return listVacReq;
    }



    public static List<VacRequest> getHistoryEmployee(int employeeID){
        List<VacRequest> listVacReq = new ArrayList<>();
        rs = null;
        String sqlQuery =   "SELECT * FROM vac_request " +
                            "WHERE fk_id_user = ? " +
                            "ORDER BY pk_id_request ASC;";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1,employeeID);
            rs= pStatement.executeQuery();

            while(rs.next()){
                VacRequest vacReq = new VacRequest();
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setFkIDUser(rs.getInt(2));
                vacReq.setStartDate(rs.getDate(3).toLocalDate());
                vacReq.setEndDate(rs.getDate(4).toLocalDate());
                vacReq.setStatus(rs.getString(5));
                vacReq.setSupervisorID(rs.getInt(6));
                listVacReq.add(vacReq);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestRead.getHistoryEmployee error: "+e);
        }

        return listVacReq;
    }



}
