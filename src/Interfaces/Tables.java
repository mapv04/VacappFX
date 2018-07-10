package Interfaces;

import java.sql.SQLException;

public interface Tables {

    void initializeTable()throws SQLException;

  //  void setSelected();

    Object getSelected();
}
