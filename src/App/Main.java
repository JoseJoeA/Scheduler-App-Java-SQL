package App;

import DB.DBConnection;
import View_Controller.AddAppointmentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author Jose Arvizu
 *
 * Javadocs is located in: javadocs/index.html
 */
/** Application for the scheduling app */
public class Main extends Application {
    /** Opens the login screen window */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Login.fxml"));
        primaryStage.setTitle("Schedule Program");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /** Main launch
     * @param args main command to launch
     */
    public static void main(String[] args) throws SQLException {

        Connection conn = DBConnection.startConnection();
//        boolean isOverlapping = AddAppointmentController.checkOverlap("12:00:00", "2020-05-28", "12:00:00", "2021-05-28");
//        if (isOverlapping){
//            System.out.println("Appointment Overlap");
//        }
//        else {
//            System.out.println("No Overlap");
//        }
//        DBQuery.setStatement(conn);
//        Statement statement = DBQuery.getStatement();
//
//        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES('US', '2021-02-22 00:00:00', 'admin', 'admin')";

        // excute
//        statement.execute(insertStatement);

        //confirm
//        if(statement.getUpdateCount() > 0)
//            System.out.println(statement.getUpdateCount() + " rows(s) affected!");
//        else
//            System.out.println("No Change");
//        DBCountries.checkDateConversion();
        launch(args);
        DBConnection.closeConnection();
    }
}
