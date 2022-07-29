package DB;

/**
 *
 *
 * @author Jose Arvizu
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/** Class for DBConnection */
public class DBConnection {

//    private static final String protocol = "jdbc";
//    private static final String vendorName = ":mysql:";
//    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
//    private static final String dbName = "WJ06y48";
//    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
//
//    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";
//
//    private static final String username = "U06y48";
//    private static final String password = "53688905992";

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost:3306/";
    private static final String dbName = "sys";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";

    private static final String username = "root";
    private static final String password = "root12345";


    private static Connection conn = null;
    /** Starts connection for database */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        }   catch (SQLException e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }   catch (ClassNotFoundException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
        }
        return conn;
    }
    /** Gets connection */
    public static Connection getConnection() {
        return conn;
    }
    /** Closes Connection */
    public static void closeConnection(){
        try {
            conn.close();
        }
        catch (Exception e){

        }

    }
}
