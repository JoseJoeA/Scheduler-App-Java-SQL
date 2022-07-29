package View_Controller;

import DB.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportContact extends Report{
    String reportGenerated = "Report Contact Generated!";
    /** Gets information for the contact report */
    public String reportByContact() {
        String loadFailed = "Failed to Create";
//        reportTextArea.clear();
        Connection conn = DBConnection.getConnection();
        try {
            StringBuilder contactReport = new StringBuilder();
            contactReport.append("Contact: Appointment ID  -  Title  -  Type  -  Description  -  Start  -  End  -  Customer ID  \n_______________________________________________________________\n");

            ResultSet contact = conn.createStatement().executeQuery(String.format("SELECT Contact_ID, Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments;"));

            while (contact.next()) {
                switch (contact.getString("Contact_ID")) {
                    case "1":
                        contactName = "Anika Costa";
                        break;
                    case "2":
                        contactName = "Daniel Garcia";
                        break;
                    case "3":
                        contactName = "Li Lee";
                        break;
                }
                contactReport.append(contactName + ": " + contact.getString("Appointment_ID") + " " + contact.getString("Title") + " " + contact.getString("Type") +
                        " " + contact.getString("Description") + " " + contact.getTimestamp("Start").toLocalDateTime() + " " + contact.getTimestamp("End").toLocalDateTime() + " " + contact.getString("Customer_ID") +"\n");
            }
            return contactReport.toString();
        } catch (SQLException e) {
            System.out.println("Error on " + e.getMessage());
            return loadFailed;
        }
    }
}
