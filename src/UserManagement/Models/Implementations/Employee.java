package UserManagement.Models.Implementations;

import UserManagement.Models.Abstracts.AEmployee;

/**
 *
 * @author migue
 */
public class Employee extends AEmployee {


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        super.id=id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        super.name=name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        super.lastName=lastName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        super.username=username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        super.email=email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        super.password=password;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        super.type=type;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        super.status= status;
    }
}
