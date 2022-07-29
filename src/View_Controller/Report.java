package View_Controller;

import DB.DBConnection;
import Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static DB.DBQuery.login;
/** Class for the Report controller for the Report FXML page.*/
public class Report implements Initializable {


    Stage stage;
    Parent par;
    Scene scene;
    String contactName;
    String reportGenerated = "Report Generated!";

//    ReportMonth reportMonth = new ReportMonth();
//    ReportContact reportContact = new ReportContact();
//    ReportCustomer reportCustomer = new ReportCustomer();
    @FXML
    public TextArea reportTextArea;

    @FXML
    private Button typeMonthBtn;

    @FXML
    private Button contactBtn;

    @FXML
    private Button aptBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private Label reportGenLbl;


    /** Returns to main menu */
    @FXML
    void onActionReturn(ActionEvent event) throws IOException {

        par = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,1000,700);
        stage.setScene(scene);
        stage.show();

    }

//    /** Gets information for the type and month report */
//    public String reportByTypeMonth() {
//        reportTextArea.clear();
//        Connection conn = DBConnection.getConnection();
//        try {
//            StringBuilder typeMonthReport = new StringBuilder();
//            typeMonthReport.append("Month-Type-Amount  \n____________________\n");
//
//            ResultSet typeMonth = conn.createStatement().executeQuery(String.format("SELECT MONTH(Start) as Month, COUNT(*) as Amount, Type FROM appointments GROUP BY MONTH(Start), Type;"));
//            while (typeMonth.next()) {
//                typeMonthReport.append(typeMonth.getString("Month") + "  " + typeMonth.getString("type") + "  " + typeMonth.getString("Amount") + "\n");
//            }
//            return typeMonthReport.toString();
//        } catch (SQLException e) {
//            System.out.println("Error on " + e.getMessage());
//            return loadFailed;
//        }
//    }
//    /** Gets information for the contact report */
//    public String reportByContact() {
//        reportTextArea.clear();
//        Connection conn = DBConnection.getConnection();
//        try {
//            StringBuilder contactReport = new StringBuilder();
//            contactReport.append("Contact: Appointment_ID  -  Title  -  Type  -  Description  -  Start  -  End  -  Customer ID  \n_______________________________________________________________\n");
//
//            ResultSet contact = conn.createStatement().executeQuery(String.format("SELECT Contact_ID, Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments;"));
//
//            while (contact.next()) {
//                switch (contact.getString("Contact_ID")) {
//                    case "1":
//                        contactName = "Anika Costa";
//                        break;
//                    case "2":
//                        contactName = "Daniel Garcia";
//                        break;
//                    case "3":
//                        contactName = "Li Lee";
//                        break;
//                }
//                contactReport.append(contactName + ": " + contact.getString("Appointment_ID") + " " + contact.getString("Title") + " " + contact.getString("Type") +
//                       " " + contact.getString("Description") + " " + contact.getTimestamp("Start").toLocalDateTime() + " " + contact.getTimestamp("End").toLocalDateTime() + " " + contact.getString("Customer_ID") +"\n");
//            }
//            return contactReport.toString();
//        } catch (SQLException e) {
//            System.out.println("Error on " + e.getMessage());
//            return loadFailed;
//        }
//        return reportContact.reportByContact();
//    }
//    /** Gets information for the customer information report */
//    public String reportByCustomer(){
//
//        reportTextArea.clear();
//        Connection conn = DBConnection.getConnection();
//        try {
//            StringBuilder cusMonthReport = new StringBuilder();
//            cusMonthReport.append("Customer ID - Name - Address - Zip - Phone  \n_________________________________________________\n");
//
//            ResultSet typeMonth = conn.createStatement().executeQuery(String.format("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone FROM customers GROUP BY Customer_ID;"));
//            while (typeMonth.next()) {
//                cusMonthReport.append(typeMonth.getString("Customer_ID") + "            " + typeMonth.getString("Customer_Name") + "               " + typeMonth.getString("Address") + "               " + typeMonth.getString("Postal_Code") + "               " + typeMonth.getString("Phone") + "\n");
//            }
//            return cusMonthReport.toString();
//        } catch (SQLException e) {
//            System.out.println("Error on " + e.getMessage());
//            return loadFailed;
//        }
//    }


    /** Custom Button event lambdas, that generate a report from what the user clicks and set text to a label to show what type of report was generated. Inheritance and polymorphism */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportGenLbl.setText("Report Generated: None");
        ReportMonth reportMonth = new ReportMonth();
        ReportContact reportContact = new ReportContact();
        ReportCustomer reportCustomer = new ReportCustomer();

        typeMonthBtn.setOnAction(event -> {
            reportGenLbl.setText("Report: " + reportMonth.reportGenerated);
            reportTextArea.clear();
            reportTextArea.setText(reportMonth.reportByTypeMonth());
        });

        contactBtn.setOnAction(event -> {
            reportGenLbl.setText("Report: " + reportContact.reportGenerated);
            reportTextArea.clear();
            reportTextArea.setText(reportContact.reportByContact());
        });

        aptBtn.setOnAction(event -> {
            reportGenLbl.setText("Report: " + reportCustomer.reportGenerated);
            reportTextArea.clear();
            reportTextArea.setText(reportCustomer.reportByCustomer());
        });

    }
}