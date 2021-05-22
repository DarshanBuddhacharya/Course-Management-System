package Helpers;

import java.sql.*;

public class DBUtils {
    public static Connection getDbConnection() throws SQLException {

        String connectionString =
        "jdbc:mysql://"+Config.dbHost+":"+Config.dbPort+"/"+Config.dbName;

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        return (Connection) DriverManager.getConnection(connectionString,Config.dbUser,Config.dbPassword);
    }
    public static Connection getDbConnectionDataBase() throws SQLException{
        String connectionString =
                "jdbc:mysql://"+Config.dbHost+":"+Config.dbPort;
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return (Connection) DriverManager.getConnection(connectionString,Config.dbUser,Config.dbPassword);
    }
}
