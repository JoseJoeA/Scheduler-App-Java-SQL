package View_Controller;
/**
 *
 *
 * @author Jose Arvizu
 * Javadocs is located in: javadocs/index.html
 */

import DB.DBConnection;
import Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static DB.DBQuery.aptFifteen;
import static DB.DBQuery.login;
/** Login Screen for C195 Project.
 *
 *
 *  FUTURE ENHANCEMENT: Would add more languages and words for people in different areas, to make it more accessible to people. Also add
 *  more checking to see if Usernames exists.
 *
 * */

public class LoginController implements Initializable {

    String username;
    String password;
    String missInfoTitle;
    String missInfoHeader;
    String missInfoContent;
    String incorrectInfoTitle;
    String incorrectInfoHeader;
    String incorrectInfoContent;
    Stage stage;
    Parent par;
    Scene scene;

    @FXML
    private Label titleLbl;
    @FXML
    private Label userLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label lanLbl;
    @FXML
    private Label timeZoneLbl;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginBtn;
    /** Logins in when the button is clicked, gets user info, records login attempt, goes to mainscreen if successful and checks if current user has an appointment soon.
     *
     * */
    @FXML void OnActionLogin(ActionEvent event) throws IOException {

        username = usernameField.getText();
        password = passwordField.getText();
        // Uses function from DBquery, for the username and password
        if (login(username, password)){

            String file = "logs_activity.txt";
            FileWriter writer = new FileWriter(file, true);
            PrintWriter outputTxt = new PrintWriter(writer);
            outputTxt.println("User: " + usernameField.getText() + " Logged in: " + LocalDateTime.now() + "Successfully");
            System.out.println(usernameField.getText() + " has logged on at " + LocalDateTime.now());
            outputTxt.close();

            Connection conn;
            try {
                conn = DBConnection.getConnection();
                ResultSet getUserInfo = conn.createStatement().executeQuery(String.format("SELECT User_ID, User_Name FROM users WHERE User_Name='%s'", username));
                getUserInfo.next();
                Users cusUsers = new Users(getUserInfo.getString("User_Name"), getUserInfo.getString("User_ID"), true);
                System.out.println("UserID: " + Users.getCusId() + " Username: " + Users.getCusUsername());
            } catch (SQLException e){
                e.printStackTrace();
            }
            System.out.println("You have logged in!");
            par = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(par,1000,700);
            stage.setScene(scene);
            stage.show();

            if (aptFifteen() == true){
                System.out.println("Appointment soon!");
            }
            else {
                System.out.println("No Appointments soon.");
            }

        }
        else {
            String file = "logs_activity.txt";
            FileWriter writer = new FileWriter(file, true);
            PrintWriter outputTxt = new PrintWriter(writer);
            outputTxt.println("Failed Login attempt: " + LocalDateTime.now());
            outputTxt.close();

            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle(missInfoTitle);
                alert.setHeaderText(missInfoHeader);
                alert.setContentText(missInfoContent);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle(incorrectInfoTitle);
                alert.setHeaderText(incorrectInfoHeader);
                alert.setContentText(incorrectInfoContent);
                alert.showAndWait();
            }
        }
    }
    /** Lambda interface showing timezone for login screen in initialize, a needed requirement. Shows the user their timezone. */
    public interface timezone_in {
        String userTz();
    }
    /** Sets text from resource bundle, if using english or french, and uses lambda to get your current timezone. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Lambda showing timezone for login screen
        timezone_in show = () -> "Timezone: " + ZoneId.systemDefault().toString();
        timeZoneLbl.setText(show.userTz());

        Locale locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("languages/login", locale);
        userLbl.setText(resourceBundle.getString("username"));
        passwordLbl.setText(resourceBundle.getString("password"));
        titleLbl.setText(resourceBundle.getString("title"));
        lanLbl.setText(resourceBundle.getString("language"));
        loginBtn.setText(resourceBundle.getString("login"));
        missInfoTitle = resourceBundle.getString("MissingInfoTitle");
        missInfoHeader = resourceBundle.getString("MissingInfoHeader");
        missInfoContent = resourceBundle.getString("MissingInfoContent");
        incorrectInfoTitle = resourceBundle.getString("IncorrectInfoTitle");
        incorrectInfoHeader = resourceBundle.getString("IncorrectInfoHeader");
        incorrectInfoContent = resourceBundle.getString("IncorrectInfoContent");
    }
}