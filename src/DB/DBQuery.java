package DB;

/**
 *
 *
 * @author Jose Arvizu
 *
 */

import static DB.DBConnection.getConnection;

import Model.Users;
import View_Controller.TimeZoneConversions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/** Class for DBQuery */
public class DBQuery {

    static ObservableList<String> countries = FXCollections.observableArrayList();
    static ObservableList<String> cities = FXCollections.observableArrayList();
    static ObservableList<String> contacts = FXCollections.observableArrayList();

    private static Statement statement;
    /** retrieves username and password from database */
    public static boolean login(String username, String password){
        try {
            PreparedStatement pst = DBConnection.startConnection().prepareStatement("SELECT * FROM users WHERE User_Name=? AND Password=?");
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Create statement */
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /** Getters */
    public static Statement getStatement()
    {
        return statement;
    }
    /** Classes for adding customer */
    public static void addCus(String name, String address, String zip, String phone, String city, String country) throws SQLException{

        ResultSet getDivisionId = getConnection().createStatement().executeQuery(String.format("SELECT Division_ID FROM first_level_divisions WHERE Division = '%S'", city));
        getDivisionId.next();

        getConnection().createStatement().executeUpdate(String.format("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES ('%s', '%s', '%s', '%s', '%s')", name, country + " Address: " + address + ", " + city, zip, phone, getDivisionId.getString("Division_ID")));
    }
    /** Class for deleting customer */
    public static void delCus(String id) throws SQLException {
        try {
            getConnection().createStatement().executeUpdate(String.format("DELETE FROM appointments WHERE Customer_ID = '%s'", id));

            getConnection().createStatement().executeUpdate(String.format("DELETE FROM customers WHERE Customer_ID = '%s'", id));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /** Class for editing customer */
    public static void editCus(String Id, String name, String address, String zip, String phone, String city, String country) throws SQLException{

        ResultSet getDivisionId = getConnection().createStatement().executeQuery(String.format("SELECT Division_ID FROM first_level_divisions WHERE Division = '%S'", city));
        getDivisionId.next();

        getConnection().createStatement().executeUpdate(String.format("UPDATE customers SET Customer_Name = '%s', Address = '%s', Postal_Code = '%s', Phone = '%s', Division_ID = '%s' WHERE Customer_ID = '%s'", name, country + " Address: " + address + ", " + city, zip, phone, getDivisionId.getString("Division_ID"), Id));
    }
    /** Class for adding appointment */
    public static void addApt(String title, String description, String location, String contact, String type, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String cusId, String userId) throws SQLException {

        ResultSet getContactId = getConnection().createStatement().executeQuery(String.format("SELECT Contact_ID FROM contacts WHERE Contact_Name = '%s'", contact));
        getContactId.next();
        String sql = String.format("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES ('%s', '%s', '%s', '%s', ?, ?, '%s', '%s', '%s')",
                title, description, location, type, cusId, userId, getContactId.getString("Contact_ID"));
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setTimestamp(1,Timestamp.valueOf(LocalDateTime.of(startDate, startTime)));
        ps.setTimestamp(2,Timestamp.valueOf(LocalDateTime.of(endDate, endTime)));
        ps.execute();


    }
    /** Class for deleting appointment */
    public static void delApt(String id) throws SQLException {

        getConnection().createStatement().executeUpdate(String.format("DELETE FROM appointments WHERE Appointment_ID = '%s'", id));

    }
    /** Class for editing appointment */
    public static void editApt(String Id, String title, String description, String location, String contact, String type, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String cusId, String userId) throws SQLException{

        ResultSet getContactId = getConnection().createStatement().executeQuery(String.format("SELECT Contact_ID FROM contacts WHERE Contact_Name = '%s'", contact));
        getContactId.next();
        String Sql = String.format("UPDATE appointments SET Title = '%s', Description = '%s', Location = '%s', Contact_ID = '%s', Type = '%s', Start = ?, End = ?, Customer_ID = '%s', User_ID = '%s' WHERE Appointment_ID = '%s'",
                title, description, location, getContactId.getString("Contact_ID"), type, cusId, userId, Id);
        PreparedStatement ps = getConnection().prepareStatement(Sql);
        ps.setTimestamp(1,Timestamp.valueOf(LocalDateTime.of(startDate, startTime)));
        ps.setTimestamp(2,Timestamp.valueOf(LocalDateTime.of(endDate, endTime)));
        ps.execute();

    }
    /** Boolean that warns if current User has an appointment soon with a customer */
    public static boolean aptFifteen(){

        LocalDateTime LocalTimeStart = LocalDateTime.now();
        LocalDateTime LocalTimeEnd = LocalDateTime.now().plusMinutes(15);

        // Converts into UTC Time
        LocalTimeStart = TimeZoneConversions.LocalToUTC(LocalTimeStart);
        LocalTimeEnd = TimeZoneConversions.LocalToUTC(LocalTimeEnd);

        // Timestamp and formatter
        final SimpleDateFormat formatC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp tsNow = Timestamp.valueOf(LocalTimeStart);
        Timestamp tsFifth = Timestamp.valueOf(LocalTimeEnd);


//        final SimpleDateFormat formatC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Timestamp tsNowUTC = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
//        Timestamp tsFifthUTC = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(15));
//
//        Timestamp tsNow = Timestamp.valueOf(LocalDateTime.now());
//        Timestamp tsFifth = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15));


//        String timeStampLocalNowUTC = formatC.format(tsNowUTC);
//        String timeStampLocalFUTC = formatC.format(tsFifthUTC);
        String timeStampLocalNow = formatC.format(tsNow);
        String timeStampLocalF = formatC.format(tsFifth);

//        System.out.println("Times non-utc converted vs utc converted: " + timeStampLocalNow + " VS " + timeStampLocalNowUTC);

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM appointments apt INNER JOIN customers cus ON apt.Customer_ID = cus.Customer_ID INNER JOIN users u ON apt.User_ID = u.User_ID WHERE apt.User_ID = ? AND apt.Start BETWEEN ? AND ?";
            PreparedStatement psApt = conn.prepareStatement(sql);
            psApt.setString(1, Users.getCusId());
            psApt.setString(2, timeStampLocalNow);
            psApt.setString(3,timeStampLocalF);
            psApt.execute();
            ResultSet rs = psApt.getResultSet();
            while (rs.next()){
                System.out.println("There is an apt within the hours, User ID: " + Users.getCusId() + " Times Compared: " + timeStampLocalNow + " between " + timeStampLocalF);
                String aptId = rs.getString("Appointment_ID");
                String aptStartDateTime = rs.getString("Start");

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Appointment Alert!");
                alert.setHeaderText("Appointment under 15 minutes!");
                alert.setContentText("Appointment ID: " + aptId + " Appointment Start UTC: " + aptStartDateTime);
                alert.showAndWait();
                return true;
            }
//            System.out.println("There is an apt within the hours, User ID: " + Users.getCusId() + " Times Compared: " + timeStampLocalNow + " between " + timeStampLocalF);
//
//            String aptId = rs.getString("Appointment_ID");
//            String aptStartDateTime = rs.getString("Start");
//
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.initModality(Modality.APPLICATION_MODAL);
//            alert.setTitle("Appointment Alert!");
//            alert.setHeaderText("Appointment Notification");
//            alert.setContentText("You have an appointment with a customer under 15 minutes! Appointment ID: " + aptId + " Appointment Start Date/Time: " + aptStartDateTime);
//            alert.showAndWait();


        } catch (SQLException e) {
            return false;
        }
        System.out.println("There is no apt, User ID: " + Users.getCusId() + ", Times Compared: " + timeStampLocalNow + " between " + timeStampLocalF);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Appointment Alert!");
        alert.setHeaderText("No Appointments");
        alert.setContentText("No Appointments within 15 minutes.");
        alert.showAndWait();
        return false;
    }


    /** Class for retrieving list of items, contact names */
    public static ObservableList<String> getContactNames() {
        try {
            contacts.removeAll(contacts);
            ResultSet contactsList = getConnection().createStatement().executeQuery("SELECT Contact_Name FROM contacts");
            while (contactsList.next()){
                contacts.add(contactsList.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
    /** Class for retrieving list of items, countries */
    public static ObservableList<String> getCountries() {
        try {
            countries.removeAll(countries);
            ResultSet countriesList = getConnection().createStatement().executeQuery("SELECT Country FROM countries");
            while (countriesList.next()){
                countries.add(countriesList.getString("Country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
    /** Class for retrieving list of items, US cities */
    public static ObservableList<String> getUsCities() {
        try {
            cities.removeAll(cities);
            ResultSet citiesList = getConnection().createStatement().executeQuery("SELECT Division FROM first_level_divisions WHERE COUNTRY_ID=1");
            while (citiesList.next()){
                cities.add(citiesList.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
    /** Class for retrieving list of items, UK Cities */
    public static ObservableList<String> getUkCities() {
        try {
            cities.removeAll(cities);
            ResultSet citiesList = getConnection().createStatement().executeQuery("SELECT Division FROM first_level_divisions WHERE COUNTRY_ID=2");
            while (citiesList.next()) {
                cities.add(citiesList.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
    /** Class for retrieving list of items, Canada cities */
    public static ObservableList<String> getCanCities() {
        try {
            cities.removeAll(cities);
            ResultSet citiesList = getConnection().createStatement().executeQuery("SELECT Division FROM first_level_divisions WHERE COUNTRY_ID=3");
            while (citiesList.next()) {
                cities.add(citiesList.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }


    static ObservableList<LocalTime> times = FXCollections.observableArrayList();
    /** Class for getting time */
    public static ObservableList<LocalTime> getTime() {
        times.clear();
        LocalTime start = LocalTime.MIDNIGHT;
        times.add(start);
        start = start.plusMinutes(15);
        while (start.isAfter(LocalTime.MIDNIGHT)){
            times.add(start);
            start = start.plusMinutes(15);
        }
        return times;
    }
/** Method to convert times to UTC */
    public static String convertTimesToUTC(String startDate, String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Make A LocalDateTime from string values
        LocalDateTime LocalTimeStart = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));


        // Converts into UTC Time
        LocalTimeStart = TimeZoneConversions.LocalToUTC(LocalTimeStart);


        // Timestamp and formatter
        final SimpleDateFormat formatC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp tsStart = Timestamp.valueOf(LocalTimeStart);


        // Just combines both date and time strings
        Timestamp tsStart2 = Timestamp.valueOf(startDate + " " + startTime);


        // Formats it into yyyy-MM-dd
        String timeStampStart= formatC.format(tsStart);

        // Formats it into yyyy-MM-dd
        String timeStampStart2 = formatC.format(tsStart2);

        return timeStampStart;
    }


}
