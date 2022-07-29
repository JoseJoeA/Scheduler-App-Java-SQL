package View_Controller;

import DB.DBConnection;
import DB.DBQuery;
import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/** Class of the Controller for the AddAppointment fxml screen.
 *
 *  FUTURE ENHANCEMENT: Make the code less messy, a bit more organized.
 *
 * */
public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent par;
    Scene scene;

    @FXML private TableView<Appointments> appointmentsTableView;
    @FXML private TableColumn<Appointments, Integer> aptIdCol;
    @FXML private TableColumn<Appointments, String> aptTitleCol;
    @FXML private TableColumn<Appointments, String> aptDesCol;
    @FXML private TableColumn<Appointments, String> aptLocCol;
    @FXML private TableColumn<Appointments, String> aptContactCol;
    @FXML private TableColumn<Appointments, String> aptTypeCol;
    @FXML private TableColumn<Appointments, String> aptStartCol;
    @FXML private TableColumn<Appointments, String> aptEndCol;
    @FXML private TableColumn<Appointments, String> aptCusIdCol;
    @FXML private TableColumn<Appointments, String> aptUserIdCol;

    ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> contactCb;

    @FXML
    private TextField aptIdField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField desField;

    @FXML
    private Label aptIDLbl;

    @FXML
    private Label titleLbl;

    @FXML
    private Label locLbl;

    @FXML
    private TextField locField;

    @FXML
    private Label contactLbl;

    @FXML
    private Label typeLbl;

    @FXML
    private TextField typeField;

    @FXML
    private Label startDateLbl;

    @FXML
    private Label startTimeLbl;

    @FXML
    private Label endTimeLbl;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimeCb;

    @FXML
    private ComboBox<LocalTime> endTimeCb;

    @FXML
    private Label cusIdLbl;

    @FXML
    private Label userIdLbl;

    @FXML
    private TextField cusIdField;

    @FXML
    private TextField userIdField;

    @FXML
    private Button addAptBtn;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label endDateLbl;

    @FXML
    private Button returnBtn;
    /** Add action for adding a next appointment to the table. */
    @FXML
    void onActionAdd(ActionEvent event) {

        try{
            String addAptId = aptIdField.getText();
            String addTitle = titleField.getText();
            String addDes = desField.getText();
            String addLoc = locField.getText();
            String addContact = contactCb.getValue();
            String addType = typeField.getText();
//            String addStartDate = startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate addStartDate = startDatePicker.getValue();
            LocalTime addStartTime = startTimeCb.getValue();
//            String addEndDate = endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate addEndDate = endDatePicker.getValue();
            LocalTime addEndTime = endTimeCb.getValue();
            String addCusId = cusIdField.getText();
            String addUserId = userIdField.getText();
            LocalDate startDateL = startDatePicker.getValue();
            LocalDate endDateL = endDatePicker.getValue();
            int sameStartTime = startTimeCb.getSelectionModel().getSelectedIndex();
            int sameEndTime = endTimeCb.getSelectionModel().getSelectedIndex();
            boolean isOverlapping = checkOverlap(addStartTime, addStartDate, addEndTime, addEndDate, addCusId);
            boolean checkBH = checkBusinessHours(addStartTime, addEndTime);

            try {
                if (addTitle.isEmpty() || addDes.isEmpty() || addLoc.isEmpty() || addContact.isEmpty() || addType.isEmpty() || addStartDate == null || addStartTime == null || addEndDate == null || addEndTime == null || addCusId.isEmpty() || addUserId.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Appointment Field");
                    alert.setHeaderText("Appointment Field Error");
                    alert.setContentText("One of your appointments fields are empty, please fill everything!");
                    alert.showAndWait();
                }
                else {
                    if(sameStartTime > sameEndTime) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Time Issue Warning!");
                        alert.setHeaderText("Appointment Time");
                        alert.setContentText("Your End Time of the appointment is greater than its Start Time!");
                        alert.showAndWait();
                    }
                    else if (sameEndTime == sameStartTime){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Time Issue Warning!");
                        alert.setHeaderText("Appointment Time");
                        alert.setContentText("Your Start Time is the same as your End Time!");
                        alert.showAndWait();
                    }
                    else if (endDateL.isBefore(startDateL)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Date Warning");
                        alert.setHeaderText("Appointment Date");
                        alert.setContentText("Your appointment end date cannot be before your start date.");
                        alert.showAndWait();
                    }
                    else {
                        if (isOverlapping && checkBH) {
                            try {
                                DBQuery.addApt(addTitle, addDes, addLoc, addContact, addType, addStartDate, addStartTime, addEndDate, addEndTime, addCusId, addUserId);

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initModality(Modality.APPLICATION_MODAL);
                                alert.setTitle("Appointment Confirm");
                                alert.setHeaderText("Appointment added");
                                alert.setContentText("You have added a new appointment!");
                                alert.showAndWait();

                                par = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(par, 1000, 700);
                                stage.setScene(scene);
                                stage.show();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            if (!checkBusinessHours(addStartTime, addEndTime)){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initModality(Modality.APPLICATION_MODAL);
                                alert.setTitle("Appointment Declined");
                                alert.setHeaderText("Appointment out of business hours");
                                alert.setContentText("Your Appointment has been declined due to being off the business hours!");
                                alert.showAndWait();
                            }
                            else if (!checkOverlap(addStartTime, addStartDate, addEndTime, addEndDate, addCusId)){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initModality(Modality.APPLICATION_MODAL);
                                alert.setTitle("Appointment Declined");
                                alert.setHeaderText("Appointment has overlapped");
                                alert.setContentText("Your Appointment has overlap with others!");
                                alert.showAndWait();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** Return action to go back main menu. */
    @FXML
    void onActionReturn(ActionEvent event) throws IOException {

        par = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,1000,700);
        stage.setScene(scene);
        stage.show();
    }

//    public static LocalDateTime stringConvertUTC(String dates, String times) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dt = LocalDateTime.parse(dates + " " + times, formatter).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
//        return dt;
//    }

    /** Checks for appointment overlap, currently has problems. */
    public static boolean checkOverlap(LocalTime startTime, LocalDate startDate, LocalTime endTime, LocalDate endDate, String customerId) {

        try {

            // Make A LocalDateTime from string values
            LocalDateTime LocalTimeStart = LocalDateTime.of(startDate, startTime);
            LocalDateTime LocalTimeEnd = LocalDateTime.of(endDate, endTime);


//            LocalDateTime LocalTimeStart = LocalDateTime.parse(databaseTimeStart, formatter);
//            LocalDateTime LocalTimeEnd = LocalDateTime.parse(dataBaseTimeEnd, formatter);

            Connection conn = DBConnection.getConnection();
//            ResultSet checkOL = conn.createStatement().executeQuery(String.format("SELECT Customer_ID, start, end FROM appointments WHERE ('%s' <= start AND '%s' >= end) OR ('%s' >= start AND '%s' <= end) OR ('%s' <= start AND '%s' >= start) OR ('%s' <= end AND '%s' >= end)",
//                    LocalTimeEnd, LocalTimeEnd, LocalTimeStart, LocalTimeStart, LocalTimeStart, LocalTimeEnd, LocalTimeStart, LocalTimeEnd));
//            ResultSet checkOL = conn.createStatement().executeQuery(String.format("SELECT Customer_ID, start, end FROM appointments WHERE start = '%s' OR end = '%s' OR start > '%s' AND start < '%s' OR end > '%s' AND end < '%s'",
//                    LocalTimeStart, LocalTimeEnd, LocalTimeStart, LocalTimeEnd, LocalTimeStart, LocalTimeEnd));
                String sql = "SELECT * FROM appointments WHERE (start = ? OR end = ? OR (start > ? AND start < ?) or (end > ? AND end < ?)) AND Customer_ID = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, Timestamp.valueOf(LocalTimeStart));
                ps.setTimestamp(2, Timestamp.valueOf(LocalTimeEnd));
                ps.setTimestamp(3, Timestamp.valueOf(LocalTimeStart));
                ps.setTimestamp(4, Timestamp.valueOf(LocalTimeEnd));
                ps.setTimestamp(5, Timestamp.valueOf(LocalTimeStart));
                ps.setTimestamp(6, Timestamp.valueOf(LocalTimeEnd));
                ps.setString(7, customerId);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()){
                    System.out.println("Overlap");
                    return false;
                }

            // If there is overlap, it will return false

        } catch (SQLException e) {
            // Will return true if there is no overlap
            System.out.println("No Overlap");
            return true;
        }
        System.out.println("No Overlap");
        return true;
    }
    /** Checks for appointment within business hours. */
    public static boolean checkBusinessHours(LocalTime startTimeCon, LocalTime endTimeCon){

        LocalTime startCheckTime = startTimeCon;
        LocalTime endCheckTime = endTimeCon;
        // Not doing timezone conversion here
        LocalTime openBusiness = LocalTime.parse("07:59:00");
        LocalTime closeBusiness = LocalTime.parse("21:59:00");

        Boolean startCorrect = startCheckTime.isAfter(openBusiness) && startCheckTime.isBefore(closeBusiness);
        Boolean endCorrect = endCheckTime.isBefore(closeBusiness) && endCheckTime.isAfter(openBusiness);


        if (startCorrect && endCorrect) {
            return true;
        }
        else {
            return false;
        }
    }
//    /** Checks user if selected days are Sunday or Saturday. */
//    private boolean checkDays(LocalDate startDateL, LocalDate endDateL){
//
//        if (startDateL.getDayOfWeek() == DayOfWeek.SUNDAY || startDateL.getDayOfWeek() == DayOfWeek.SATURDAY || endDateL.getDayOfWeek() == DayOfWeek.SUNDAY || endDateL.getDayOfWeek() == DayOfWeek.SATURDAY){
//            return false;
//        }
//        else {
//            return true;
//        }
//    }



    /** Sets the the fields when first initialized. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aptIdField.setDisable(true);
        contactCb.setItems(DBQuery.getContactNames());
        startTimeCb.setItems(DBQuery.getTime());
        endTimeCb.setItems(DBQuery.getTime());
        contactCb.getSelectionModel().selectFirst();
        startTimeCb.getSelectionModel().selectFirst();
        endTimeCb.getSelectionModel().selectFirst();
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        Connection conn;
        try {
            conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID FROM appointments");

            while (rs.next()){
                appointmentsList.add(new Appointments(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Contact_ID"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getString("Customer_ID"),
                        rs.getString("User_ID")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        aptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptDesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        aptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        aptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        aptCusIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        aptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        appointmentsTableView.setItems(appointmentsList);
    }
}
