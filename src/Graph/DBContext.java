/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
class DBContext {

    String instance = "";
    String serverName = "localhost";
    String port = "1433";
    String dbName = "Assignment6";
    String userID = "sa";
    String password = "12345";

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://" + serverName + ":" + port + "\\"
                + instance + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
        if (instance == null || instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + port + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
        }

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(url, userID, password);
    }
}
