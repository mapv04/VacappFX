/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.MySQL;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author migue
 */
public class ManagerController {
    
    MySQL mysql= new MySQL();
    ResultSet rs= null;
    Connection conn= mysql.getConnection();
    PreparedStatement preparedStatement;
    
    public void getAllEmployees(List<User> employeeList)throws SQLException{
        //List<User> employeeList= new ArrayList<>();
        String sql="select * from usuario";
        preparedStatement=conn.prepareStatement(sql);
        rs= preparedStatement.executeQuery();
        while(rs.next()){
            User employee= new User();
            employee.setId(rs.getInt("pk_id_user"));
            employee.setName(rs.getString("name_user"));
            employee.setLastName(rs.getString("last_name"));
            employee.setStatus(rs.getInt("status_user"));
            employee.setType(rs.getInt("type_user"));
            employeeList.add(employee);
        }
        //return employeeList;
    }
}
