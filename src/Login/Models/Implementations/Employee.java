package Login.Models.Implementations;

import Login.Models.Abstracts.AEmployee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee extends AEmployee {

   public Employee(ResultSet rs) throws SQLException {
       super.setId(rs.getInt(1));
       super.setName(rs.getString(2));
       super.setLastName(rs.getString(3));
       super.setUsername(rs.getString(4));
       super.setEmail(rs.getString(5));
       super.setPassword(rs.getString(6));
       super.setType(rs.getInt(7));
       super.setStatus(rs.getInt(8));
   }

   public Employee(){
   }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getResponse() {
        return super.getResponse();
    }

    @Override
    public void setResponse(String response) {
        super.setResponse(response);
    }

    @Override
    public int getQuestion() {
        return super.getQuestion();
    }

    @Override
    public void setQuestion(int question) {
        super.setQuestion(question);
    }

    @Override
    public int getType() {
        return super.getType();
    }

    @Override
    public void setType(int type) {
        super.setType(type);
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(int status) {
        super.setStatus(status);
    }
}
