package View_Controller;

import DB.DBConnection;
import DB.DBQuery;
import Model.Appointments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;
/** Class for the main menu controller for the MainMenu FXML page.
 *
 *  FUTURE ENHANCEMENT: Make the menu look cleaner and more nice.
 *
 * */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent par;
    Scene scene;

    @FXML
    private DatePicker calendarDatePicker;

    @FXML
    private RadioButton allRadioBtn;

    @FXML
    private RadioButton monthRadioBtn;

    @FXML
    private RadioButton weekRadioBtn;

    private boolean calWeek;
    private boolean calMonth;
    @FXML
    private ToggleGroup calendarGroup;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addCusBtn;

    @FXML
    private Button modCusBtn;

    @FXML
    private Button addAptBtn;

    @FXML
    private Button modAptBtn;

    @FXML
    private Button reportBtn;

    @FXML private TableView<Appointments> calendarTableView;
    @FXML private TableColumn<Appointments, Integer> aptIdCol;
    @FXML private TableColumn<Appointments, String> aptTitleCol;
    @FXML private TableColumn<Appointments, String> aptDesCol;
    @FXML private TableColumn<Appointments, String> aptLocCol;
    @FXML private TableColumn<Appointments, String> aptContactCol;
    @FXML private TableColumn<Appointments, String> aptTypeCol;
    @FXML private TableColumn<Appointments, String> aptStartCol;
    @FXML private TableColumn<Appointments, String> aptEndCol;
    @FXML private TableColumn<Appointments, String> aptCusIdCol;

    ObservableList<Appointments> appointmentCal = FXCollections.observableArrayList();
    /** Action when either date is selected. */
    @FXML
    private void onActionDateSelected (ActionEvent event) throws IOException {
        if (calMonth) {
            viewMonthly();
        }
        else if (calWeek) {
            viewWeekly();
        }
        else {
            viewAll();
        }
    }
    /** Changes table and organizes it to monthly when selected. */
    public void viewMonthly(){

        calMonth = true;
        calWeek = false;
        LocalDate datePicked = calendarDatePicker.getValue();
        String monthSelected = datePicked.toString().substring(5,7);

        searchTextField.setText(null);
        searchTextField.setDisable(true);

        Connection conn;
        try {
            appointmentCal.clear();
            conn = DBConnection.getConnection();
            ResultSet getByMonth = conn.createStatement().executeQuery(String.format("SELECT Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID " +
                    "FROM appointments WHERE MONTH(Start) = '%s' ORDER BY Start", monthSelected));

            while (getByMonth.next()){
                appointmentCal.add(new Appointments(getByMonth.getInt("Appointment_ID"),
                                                getByMonth.getString("Title"),
                                                getByMonth.getString("Description"),
                                                getByMonth.getString("Location"),
                                                getByMonth.getString("Contact_ID"),
                                                getByMonth.getString("Type"),
                                                getByMonth.getTimestamp("Start").toLocalDateTime(),
                                                getByMonth.getTimestamp("End").toLocalDateTime(),
                                                getByMonth.getString("Customer_ID"),
                                                getByMonth.getString("User_ID")));
            }
            calendarTableView.setItems(appointmentCal);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    /** Changes table and organizes it to weekly when selected. First day of the week starts on sunday. */
    public void viewWeekly(){

        calMonth = false;
        calWeek = true;
        LocalDate datePicked = calendarDatePicker.getValue();
        WeekFields calendarWeek = WeekFields.of(Locale.US);
        int weekNum = datePicked.get(calendarWeek.weekOfWeekBasedYear());
        String stringWeekNum = Integer.toString(weekNum);

        searchTextField.setText(null);
        searchTextField.setDisable(true);

        Connection conn;
        try {
            appointmentCal.clear();
            conn = DBConnection.getConnection();
            ResultSet getByMonth = conn.createStatement().executeQuery(String.format("SELECT Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID " +
                    "FROM appointments WHERE WEEK(DATE(Start)) + 1 = '%s' ORDER BY Start", stringWeekNum));

            while (getByMonth.next()){
                appointmentCal.add(new Appointments(getByMonth.getInt("Appointment_ID"),
                        getByMonth.getString("Title"),
                        getByMonth.getString("Description"),
                        getByMonth.getString("Location"),
                        getByMonth.getString("Contact_ID"),
                        getByMonth.getString("Type"),
                        getByMonth.getTimestamp("Start").toLocalDateTime(),
                        getByMonth.getTimestamp("End").toLocalDateTime(),
                        getByMonth.getString("Customer_ID"),
                        getByMonth.getString("User_ID")));
            }
            calendarTableView.setItems(appointmentCal);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /** Changes table and organizes it normally showing everything. */
    public void viewAll(){

        calMonth = false;
        calWeek = false;

        searchTextField.setDisable(false);

        Connection conn;
        try {
            appointmentCal.clear();
            conn = DBConnection.getConnection();
            ResultSet getByMonth = conn.createStatement().executeQuery(String.format("SELECT Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID FROM appointments"));

            while (getByMonth.next()){
                appointmentCal.add(new Appointments(getByMonth.getInt("Appointment_ID"),
                        getByMonth.getString("Title"),
                        getByMonth.getString("Description"),
                        getByMonth.getString("Location"),
                        getByMonth.getString("Contact_ID"),
                        getByMonth.getString("Type"),
                        getByMonth.getTimestamp("Start").toLocalDateTime(),
                        getByMonth.getTimestamp("End").toLocalDateTime(),
                        getByMonth.getString("Customer_ID"),
                        getByMonth.getString("User_ID")));
            }
            calendarTableView.setItems(appointmentCal);

            FilteredList<Appointments> filteredData = new FilteredList<>(appointmentCal, b -> true);
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Appointments -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String searchWord = newValue.toLowerCase();
                    if (Appointments.getTitle().toLowerCase().contains(searchWord) || Appointments.getDescription().toLowerCase().contains(searchWord)) {
                        return true;
                    }
                    else
                        return false;
                });
            });
            SortedList<Appointments> sortedList = new SortedList<>(filteredData);

            sortedList.comparatorProperty().bind(calendarTableView.comparatorProperty());
            calendarTableView.setItems(sortedList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /** Goes to the viewAll list in radio button. */
    @FXML private void onActionViewAllRadio (ActionEvent event) throws IOException {
        viewAll();
    }
    /** Goes to the viewMonthly list in radio button. */
    @FXML private void onActionViewMonthlyRadio (ActionEvent event) throws IOException {
        viewMonthly();
    }
    /** Goes to the viewWeekly list in radio button. */
    @FXML private void onActionViewWeeklyRadio (ActionEvent event) throws IOException {
        viewWeekly();
    }

    /** Goes to Add appointment scene. */
    @FXML
    void onActionAddApt(ActionEvent event) throws IOException {
        par = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,950,700);
        stage.setScene(scene);
        stage.show();
    }
    /** Goes to Add customer scene. */
    @FXML
    void onActionAddCus(ActionEvent event) throws IOException {

        par = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,700,800);
        stage.setScene(scene);
        stage.show();

    }
    /** Goes to Modify appointment scene. */
    @FXML
    void onActionModApt(ActionEvent event) throws IOException {

        par = FXMLLoader.load(getClass().getResource("ModifyAppointment.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,950,700);
        stage.setScene(scene);
        stage.show();

    }
    /** Goes to Modify customer scene. */
    @FXML
    void onActionModCus(ActionEvent event) throws IOException {

        par = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,700,800);
        stage.setScene(scene);
        stage.show();
    }
    /** Goes to Report scene. */
    @FXML
    void onActionReport(ActionEvent event) throws IOException {
        par = FXMLLoader.load(getClass().getResource("Report.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,600,550);
        stage.setScene(scene);
        stage.show();
    }
    /** Sets the the fields when first initialized. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        calendarGroup = new ToggleGroup();
        this.weekRadioBtn.setToggleGroup(calendarGroup);
        this.monthRadioBtn.setToggleGroup(calendarGroup);
        this.allRadioBtn.setToggleGroup(calendarGroup);
        this.allRadioBtn.setSelected(true);
        searchTextField.setDisable(false);

        calendarDatePicker.setValue(LocalDate.now());

        calMonth = false;
        calWeek = false;

        viewAll();

        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        aptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptDesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        aptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        aptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        aptCusIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
//
//        calendarTableView.setItems(appointmentCal);
//
//        FilteredList<Appointments> filteredData = new FilteredList<>(appointmentCal, b -> true);
//        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//        filteredData.setPredicate(Appointments -> {
//            if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
//                return true;
//            }
//
//            String searchWord = newValue.toLowerCase();
//            if (Appointments.getTitle().toLowerCase().contains(searchWord)) {
//                return true;
//            }
//            else
//                return false;
//        });
//    });
//        SortedList<Appointments> sortedList = new SortedList<>(filteredData);
//
//        sortedList.comparatorProperty().bind(calendarTableView.comparatorProperty());
//        calendarTableView.setItems(sortedList);
    }
}
