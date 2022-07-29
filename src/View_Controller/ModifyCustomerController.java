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
/** Class of the Controller for the ModifyCustomer fxml screen.
 *
 *  FUTURE ENHANCEMENT: Make the code less messy, a bit more organized.
 *
 * */
public class ModifyCustomerController implements Initializable {

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
    private Button modBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button returnBtn;
    /** Save action for saving customer to the table, when a customer has been selected. */
    @FXML
    void onActionSaveCustomer(ActionEvent event) {

        try {
            String id = idField.getText();
            String editName = nameField.getText();
            String editAddress = addressField.getText();
            String editZip = zipField.getText();
            String editPhone = phoneField.getText();
            String editCity = cityCb.getValue().toString();
            String editCountry = countryCb.getValue().toString();
            try {
                if(editName.isEmpty() || editAddress.isEmpty() || editZip.isEmpty() || editPhone.isEmpty() || editCity.isEmpty() || editCountry.isEmpty()) {
//                    DBQuery.editCus(id, editName,editAddress,editZip,editPhone,editCity,editCountry);

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Customer Empty");
                    alert.setHeaderText("Customer Field error");
                    alert.setContentText("You have not filled on of the fields in, please fill information.");
                    alert.showAndWait();

//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.initModality(Modality.APPLICATION_MODAL);
//                    alert.setTitle("Customer Confirm");
//                    alert.setHeaderText("Customer updated");
//                    alert.setContentText("You have updated a customer!");
//                    alert.showAndWait();
//
//                    par = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
//                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(par,700,800);
//                    stage.setScene(scene);
//                    stage.show();
                }

                else {
                    DBQuery.editCus(id, editName,editAddress,editZip,editPhone,editCity,editCountry);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Customer Confirm");
                    alert.setHeaderText("Customer updated");
                    alert.setContentText("You have updated a customer!");
                    alert.showAndWait();

                    par = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
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
    /** Mod action to modify selected customer. Opens up textfields and populates data. */
    @FXML
    void onActionModCustomer(ActionEvent event) throws IOException {
        Customers selCustomer = customersTableView.getSelectionModel().getSelectedItem();
        try {
            idField.setText(String.valueOf(selCustomer.getId()));
            nameField.setText(String.valueOf(selCustomer.getName()));
            String ss = String.valueOf(selCustomer.getAddress()).replaceAll(".*\\:|\\,.*","");
            ss = ss.trim();
            addressField.setText(ss);
            zipField.setText(String.valueOf(selCustomer.getZip()));
            phoneField.setText(String.valueOf(selCustomer.getPhone()));
            String sco = String.valueOf(selCustomer.getAddress().replaceAll("\\ .*", ""));
            countryCb.setValue(sco);
            selectCcList();
            String sc = String.valueOf(selCustomer.getAddress().replaceAll(".*\\,", ""));
            sc = sc.trim();
            cityCb.setValue(sc);
            nameField.setDisable(false);
            addressField.setDisable(false);
            zipField.setDisable(false);
            phoneField.setDisable(false);
            countryCb.setDisable(false);
            cityCb.setDisable(false);
            saveBtn.setDisable(false);
            deleteBtn.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Customer selected empty");
            alert.setHeaderText("Customer not selected");
            alert.setContentText("You have not selected a customer, click on a customer to begin!");
            alert.showAndWait();
        }
    }
    /** Delete action to delete selected customer. */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {
        Customers selDeleteCustomer = customersTableView.getSelectionModel().getSelectedItem();

        try {
            String id = String.valueOf(selDeleteCustomer.getId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Customer Deletion");
            alert.setHeaderText("Customer deletion confirmation");
            alert.setContentText("Are you sure you want to delete the customer?");
            Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    DBQuery.delCus(id);

                    par = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(par,700,800);
                    stage.setScene(scene);
                    stage.show();

                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.initModality(Modality.APPLICATION_MODAL);
                    alert2.setTitle("Customer Deleted");
                    alert2.setHeaderText("Customer deletion complete");
                    alert2.setContentText("You have deleted the customer and its corresponding appointments!");
                    alert2.showAndWait();
                }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Customer selected empty");
            alert.setHeaderText("Customer not selected");
            alert.setContentText("You have not selected a customer, click on a customer to begin!");
            alert.showAndWait();
        }
    }
    /** List action for retrieving list of cities from countries. */
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



    /** Sets the the fields when first initialized. Populates table, disables certain buttons and fields. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        idField.setDisable(true);
        nameField.setDisable(true);
        addressField.setDisable(true);
        zipField.setDisable(true);
        phoneField.setDisable(true);
        countryCb.setDisable(true);
        cityCb.setDisable(true);
        countryCb.setItems(DBQuery.getCountries());
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
