package View_Controller;

import DB.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMonth extends Report{
    String reportGenerated = "Report Month Generated!";
    /** Gets information for the type and month report */
    public String reportByTypeMonth() {
        String loadFailed = "Failed to Create";
//        reportTextArea.clear();
        Connection conn = DBConnection.getConnection();
        try {
            StringBuilder typeMonthReport = new StringBuilder();
            typeMonthReport.append("Month-Type-Amount  \n____________________\n");

            ResultSet typeMonth = conn.createStatement().executeQuery(String.format("SELECT MONTH(Start) as Month, COUNT(*) as Amount, Type FROM appointments GROUP BY MONTH(Start), Type;"));
            while (typeMonth.next()) {
                typeMonthReport.append(typeMonth.getString("Month") + "  " + typeMonth.getString("type") + "  " + typeMonth.getString("Amount") + "\n");
            }
            return typeMonthReport.toString();
        } catch (SQLException e) {
            System.out.println("Error on " + e.getMessage());
            return loadFailed;
        }
    }
}
