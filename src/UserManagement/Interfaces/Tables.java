package UserManagement.Interfaces;

import java.sql.SQLException;

public interface Tables {

    void initializeTable()throws SQLException;


    Object getSelected();
}
