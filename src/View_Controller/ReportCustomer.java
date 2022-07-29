package View_Controller;

import DB.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportCustomer extends Report{
    String reportGenerated = "Report Customer Generated!";
    /** Gets information for the customer information report */
    public String reportByCustomer(){
        String loadFailed = "Failed to Create";
//        reportTextArea.clear();
        Connection conn = DBConnection.getConnection();
        try {
            StringBuilder cusMonthReport = new StringBuilder();
            cusMonthReport.append("Customer ID - Name - Address - Zip - Phone  \n_________________________________________________\n");

            ResultSet typeMonth = conn.createStatement().executeQuery(String.format("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone FROM customers GROUP BY Customer_ID;"));
            while (typeMonth.next()) {
                cusMonthReport.append(typeMonth.getString("Customer_ID") + "            " + typeMonth.getString("Customer_Name") + "               " + typeMonth.getString("Address") + "               " + typeMonth.getString("Postal_Code") + "               " + typeMonth.getString("Phone") + "\n");
            }
            return cusMonthReport.toString();
        } catch (SQLException e) {
            System.out.println("Error on " + e.getMessage());
            return loadFailed;
        }
    }
}
