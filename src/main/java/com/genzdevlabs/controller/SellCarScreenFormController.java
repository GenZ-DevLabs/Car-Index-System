package com.genzdevlabs.controller;

import com.genzdevlabs.dto.Car;
import com.genzdevlabs.dto.Customer;
import com.genzdevlabs.dto.tm.OrderTM;
import com.genzdevlabs.model.CarModel;
import com.genzdevlabs.model.CustomerModel;
import com.genzdevlabs.model.OrderModel;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class SellCarScreenFormController implements Initializable {

    @FXML
    private AnchorPane orderPane;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnSellCar;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colCapacity;

    @FXML
    private TableColumn<?, ?> colColour;

    @FXML
    private TableColumn<?, ?> colFuel;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colReg;

    @FXML
    private TableColumn<?, ?> colYear;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private TableView<Car> tblShowAll;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtColour;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtReg;

    @FXML
    private TextField txtSearchCar;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TextField txtSearchCustomer;

    @FXML
    void CustomerFormOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void SellCarOnAction(ActionEvent event) {

        String oid = lblOrderId.getText();
        String nic = txtNic.getText();
        String reg = txtReg.getText();
        String name = txtName.getText();
        String brand = txtBrand.getText();
        String model =  txtModel.getText();
        String colour = txtColour.getText();
        String date = lblDate.getText().toString();

        var sellCar = new OrderTM(oid, nic, reg, name, brand, model, colour, date);

        try {
            boolean isSaved = OrderModel.save(sellCar);
            if (isSaved){
                boolean isUpdated = OrderModel.updateStatus(reg);
                if(isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "CAR Sold Successfully").show();
                    getAll();

                    txtNic.setText("");
                    txtName.setText("");
                    txtReg.setText("");
                    txtBrand.setText("");
                    txtColour.setText("");
                    txtModel.setText("");

                    new KeyFrame(Duration.seconds(0.1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void getItemsOnMouseClick(MouseEvent event) throws SQLException {
        Integer index = tblShowAll.getSelectionModel().getSelectedIndex();

        txtBrand.setText(colBrand.getCellData(index).toString());
        txtModel.setText(colModel.getCellData(index).toString());
        txtReg.setText(colReg.getCellData(index).toString());
        txtColour.setText(colColour.getCellData(index).toString());
    }

    @FXML
    void setOnKeyPressed(MouseEvent event) throws SQLException {
        searchFilter(txtSearchCar);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setCellValueFactory1();
        getAll();
        getAll1();
        getDate();
        generateNextOrderId();
    }

    void setCellValueFactory() {
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colReg.setCellValueFactory(new PropertyValueFactory<>("reg"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colFuel.setCellValueFactory(new PropertyValueFactory<>("fuel"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("colour"));
    }

    void setCellValueFactory1() {
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    void getAll() {
        try {
            ObservableList<Car> obList = FXCollections.observableArrayList();
            List<Car> cusList = CarModel.getAllUnSold();

            for (Car car : cusList) {
                obList.add(new Car(
                        car.getBrand(),
                        car.getModel(),
                        car.getReg(),
                        car.getYear(),
                        car.getFuel(),
                        car.getCapacity(),
                        car.getColour(),
                        car.getStatus()
                ));
            }
            tblShowAll.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Loading Error!!!").show();
        }
    }

    void getAll1() {
        try {
            ObservableList<Customer> obList = FXCollections.observableArrayList();
            List<Customer> cusList = CustomerModel.getAll();

            for (Customer customer : cusList) {
                obList.add(new Customer(
                        customer.getNic(),
                        customer.getName(),
                        customer.getMobile(),
                        customer.getEmail(),
                        customer.getEmail()
                ));
            }
            tblCustomer.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Loading Error!!!").show();
        }
    }

    public void searchFilter(TextField search) throws SQLException {
        FilteredList<Car> filteredData = new FilteredList<>(tblShowAll.getItems(), b -> true);

        search.setOnKeyPressed(keyEvent -> {
            search.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate(Car -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    if (Car.getBrand().toLowerCase().indexOf(searchKeyWord)>-1) {
                        return true;
                    } else if (Car.getModel().toLowerCase().indexOf(searchKeyWord)>-1) {
                        return true;
                    } else if (Car.getReg().toLowerCase().indexOf(searchKeyWord)>-1){
                        return true;
                    }else if (Car.getYear().toLowerCase().indexOf(searchKeyWord)>-1){
                        return true;
                    } else if (Car.getFuel().toLowerCase().indexOf(searchKeyWord) > -1) {
                        return true;
                    } else if (Car.getCapacity().toLowerCase().indexOf(searchKeyWord) > -1) {
                        return true;
                    } else if (Car.getColour().toLowerCase().indexOf(searchKeyWord) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }));

            SortedList<Car> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(tblShowAll.comparatorProperty());
            tblShowAll.setItems(sortedList);
        });
    }

    @FXML
    void searcCustomerOnMouseClick(MouseEvent event) throws SQLException {
        searchFilterCustomer(txtSearchCustomer);
    }

    public void searchFilterCustomer(TextField search) throws SQLException {
        FilteredList<Customer> filteredData = new FilteredList<>(tblCustomer.getItems(), b -> true);

        search.setOnKeyPressed(keyEvent -> {
            search.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate(Customer -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    if (Customer.getNic().toLowerCase().indexOf(searchKeyWord)>-1) {
                        return true;
                    } else if (Customer.getName().toLowerCase().indexOf(searchKeyWord)>-1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }));

            SortedList<Customer> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(tblCustomer.comparatorProperty());
            tblCustomer.setItems(sortedList);
        });
    }

    @FXML
    void getCustomerDetailsOnMouseClick(MouseEvent event) {
        Integer index = tblCustomer.getSelectionModel().getSelectedIndex();

        txtNic.setText(colNic.getCellData(index).toString());
        txtName.setText(colName.getCellData(index).toString());
    }

    void getDate(){
//        Date date = new Date();
//        java.sql.Date sqlDate = new java.sql.Date(date.getDate());
//        lblDate.setText(String.valueOf(sqlDate));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        lblDate.setText(formatter.format(date));
    }

    private void generateNextOrderId() {
        try {
            String nextId = OrderModel.generateNextOrderId();
            lblOrderId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
