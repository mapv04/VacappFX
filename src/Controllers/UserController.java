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
import java.util.Arrays;

/**
 *
 * @author migue
 */
public class UserController {
    MySQL mysql= new MySQL();
    ResultSet rs= null;
    Connection conn= mysql.getConnection();
    PreparedStatement preparedStatement;
    
     public boolean validateLogin(String username,String password)throws SQLException{//this method validate the username and the password
        String sql="select * from usuario";
        preparedStatement= conn.prepareStatement(sql);
        rs=preparedStatement.executeQuery();
        boolean isCorrect;
        while(rs.next()){
            if(rs.getString("username").equals(username)){//validate the username
                String correct=rs.getString("password_user");//get the correct password
                
                
                if(password.length() != correct.length()){//if the length is diferent then the password is incorrect
                    isCorrect=false;
                }
                else{
                    isCorrect=password.equals(correct);//compare the password that the user provide whit the correct one
                }
               
                if(isCorrect==true){
                    return true;//password is correct
            
                }
                
            }
            
        }
        return false;
    }
     
     public boolean validateStatus(String user)throws SQLException{//this method validate the status, if its 0 then the user is desactivate
         String sql="select status_user from usuario where username='"+user+"';";
         preparedStatement=conn.prepareStatement(sql);
         rs=preparedStatement.executeQuery();
         boolean isCorrect;
         while(rs.next()){
             int status;
             if(rs.getInt("status_user")==1)
                 return true;//the user is activated
             else
                 return false;//the user is desactivated
             
                 
         }
         return false;//user desactivated
     }
     
     public int validateType(String user)throws SQLException{
         String sql="select type_user from usuario where username='"+user+"';";
         preparedStatement=conn.prepareStatement(sql);
         rs=preparedStatement.executeQuery();
         while(rs.next()){
             int type=rs.getInt("type_user");
             return type;//return the type, Manager, Supervisor or Employee
         }
         return 5;//return other number just in case that an error 
     }
     
     
     /*
     TODO
     this method is from the register class
     it should be in another class
     */
     public  boolean searchUserExist(User user)throws SQLException{//this method validate that the user entered en the register not exist
         String sql="select username, email from usuario where username='"+user.getUsername()+"' or email='"+
                 user.getEmail()+"';";
         preparedStatement=conn.prepareStatement(sql);
         rs=preparedStatement.executeQuery();
         
         return rs.first();//true if the user already exists
         
     }
     
     
     
     /*
     TODO
     this method is from the register class, it should in another class
     */
     public void addNewUser(User user)throws SQLException{//this method add a new user
         String sql="insert into usuario(name_user, last_name, username, email, password_user, type_user, status_user) "
                 + "values ('"+user.getName()+"','"+user.getLastName()+"','"+user.getUsername()+"','"+user.getEmail()+"','"
                 +user.getPassword()+"','"+user.getType()+"','"+user.getStatus()+"');";
         preparedStatement=conn.prepareStatement(sql);
         preparedStatement.executeUpdate();
               
     }
     
     
}

