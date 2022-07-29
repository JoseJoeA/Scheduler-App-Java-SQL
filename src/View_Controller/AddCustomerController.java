package View_Controller;

import DB.DBConnection;
import DB.DBQuery;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Class of the Controller for the AddCustomer fxml screen.
 *
 * FUTURE ENHANCEMENT: Make the code less messy, a bit more organized.
 *
 * */

public class AddCustomerController implements Initializable {

    Stage stage;
    Parent par;
    Scene scene;

    @FXML private TableView<Customers> customersTableView;
    @FXML private TableColumn<Customers, Integer> customerIdCol;
    @FXML private TableColumn<Customers, String> customerNameCol;
    @FXML private TableColumn<Customers, String> customerAddressCol;
    @FXML private TableColumn<Customers, String> customerPostalCol;
    @FXML private TableColumn<Customers, String> customerPhoneCol;

    ObservableList<Customers> customersList = FXCollections.observableArrayList();

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField zipField;

    @FXML
    private TextField idField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label idLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Label addressLbl;

    @FXML
    private Label zipLbl;

    @FXML
    private Label phoneLbl;

    @FXML
    private ComboBox countryCb;

    @FXML
    private ComboBox cityCb;

    @FXML
    private Button addBtn;

    @FXML
    private Button returnBtn;
    /** Add action for adding customer to the table. */
    @FXML
    void onActionAddCustomer(ActionEvent event) {

        try {
            String addName = nameField.getText();
            String addAddress = addressField.getText();
            String addZip = zipField.getText();
            String addPhone = phoneField.getText();
            String addCity = cityCb.getValue().toString();
            String addCountry = countryCb.getValue().toString();
            try {
                if(addName.isEmpty() || addAddress.isEmpty() || addZip.isEmpty() || addPhone.isEmpty() || addCity.isEmpty() || addCountry.isEmpty()) {
//                    DBQuery.addCus(addName,addAddress,addZip,addPhone,addCity,addCountry);
//
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.initModality(Modality.APPLICATION_MODAL);
//                    alert.setTitle("Customer Confirm");
//                    alert.setHeaderText("Customer added");
//                    alert.setContentText("You have added a new customer!");
//                    alert.showAndWait();
//
//                    par = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
//                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(par,700,800);
//                    stage.setScene(scene);
//                    stage.show();

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Customer Empty");
                        alert.setHeaderText("Customer Field error");
                        alert.setContentText("You have not filled on of the fields in, please fill information.");
                        alert.showAndWait();

                }
                else {

                        DBQuery.addCus(addName,addAddress,addZip,addPhone,addCity,addCountry);

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Customer Confirm");
                        alert.setHeaderText("Customer added");
                        alert.setContentText("You have added a new customer!");
                        alert.showAndWait();

                        par = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(par,1000,700);
                        stage.setScene(scene);
                        stage.show();

//                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                    alert.initModality(Modality.APPLICATION_MODAL);
//                    alert.setTitle("Customer Empty");
//                    alert.setHeaderText("Customer Field error");
//                    alert.setContentText("You have not filled on of the fields in, please fill information.");
//                    alert.showAndWait();
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** Returns action to main menu. */
    @FXML
    void onActionReturn(ActionEvent event) throws IOException {

        par = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,1000,700);
        stage.setScene(scene);
        stage.show();

    }
    /** List action for retrieving list of cities from countries */
    @FXML
    void onActionSelectCountry(ActionEvent event) throws IOException {
        String selCountry = countryCb.getValue().toString();
        System.out.println(selCountry);
        if (selCountry.equals("U.S"))
        {
            cityCb.setItems(DBQuery.getUsCities());
            cityCb.getSelectionModel().selectFirst();
        }
        else if (selCountry.equals("UK"))
        {
            cityCb.setItems(DBQuery.getUkCities());
            cityCb.getSelectionModel().selectFirst();
        }
        else
        {
            cityCb.setItems(DBQuery.getCanCities());
            cityCb.getSelectionModel().selectFirst();
        }
    }
    /** Method same as the onActionSelectCountry event, which filters cities depending on the country that is selected. */
    private void selectCcList() {

        String selCountry = countryCb.getValue().toString();
        System.out.println(selCountry);
        if (selCountry.equals("U.S"))
        {
            cityCb.setItems(DBQuery.getUsCities());
            cityCb.getSelectionModel().selectFirst();
        }
        else if (selCountry.equals("UK"))
        {
            cityCb.setItems(DBQuery.getUkCities());
            cityCb.getSelectionModel().selectFirst();
        }
        else
        {
            cityCb.setItems(DBQuery.getCanCities());
            cityCb.getSelectionModel().selectFirst();
        }
    }

    /** Sets the the fields when first initialized. Populates data and combo lists from database. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idField.setDisable(true);

        countryCb.setItems(DBQuery.getCountries());
        countryCb.getSelectionModel().selectFirst();
        selectCcList();
        //Fills table with database data
        Connection conn;
        try {
            conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone FROM customers");
            while (rs.next()){
                customersList.add(new Customers(rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customersTableView.setItems(customersList);

    }


}
