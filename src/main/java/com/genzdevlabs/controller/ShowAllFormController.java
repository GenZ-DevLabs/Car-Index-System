package com.genzdevlabs.controller;

import com.genzdevlabs.dto.Car;
import com.genzdevlabs.model.CarModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllFormController implements Initializable {

    @FXML
    private TableView<Car> tblShowAll;

    @FXML
    private JFXComboBox<String> comFuel;

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
    private TableColumn<?, ?> colReg;

    @FXML
    private TableColumn<?, ?> colYear;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtCapa;

    @FXML
    private TextField txtColour;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtReg;


    @FXML
    private TextField txtYear;

    @FXML
    private Label txtStatus;

    @FXML
    void DeleteOnAction(ActionEvent event) {
        String reg = txtReg.getText();

        try {
            boolean isDelete = CarModel.delete(reg);
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION, "Car Deleted Successfully").show();
                getAll();

                txtModel.setText("");
                txtBrand.setText("");
                txtReg.setText("");
                txtYear.setText("");
                comFuel.setValue("");
                txtCapa.setText("");
                txtColour.setText("");
                txtStatus.setText("");
            }else{
                new Alert(Alert.AlertType.ERROR,"Delete Error").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {

        String brand = txtBrand.getText();
        String model = txtModel.getText();
        String reg = txtReg.getText();
        String year =txtYear.getText();
        String fuel = comFuel.getValue();
        String capa =txtCapa.getText();
        String colour =txtColour.getText();
        String status = txtStatus.getText();

        var car = new Car(brand, model, reg, year, fuel, capa, colour, status);

        try {
            boolean isUpdate = CarModel.update(car);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "Car Updated Successfully").show();
                getAll();

                txtModel.setText("");
                txtBrand.setText("");
                txtReg.setText("");
                txtYear.setText("");
                comFuel.setValue("");
                txtCapa.setText("");
                txtColour.setText("");
                txtStatus.setText("");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void getItemsOnMouseClick(MouseEvent event) {
        Integer index = tblShowAll.getSelectionModel().getSelectedIndex();

        txtBrand.setText(colBrand.getCellData(index).toString());
        txtModel.setText(colModel.getCellData(index).toString());
        txtReg.setText(colReg.getCellData(index).toString());
        txtYear.setText(colYear.getCellData(index).toString());
        txtCapa.setText(colCapacity.getCellData(index).toString());
        txtColour.setText(colColour.getCellData(index).toString());
        comFuel.setValue(colFuel.getCellData(index).toString());
        txtStatus.setText(colStatus.getCellData(index).toString());
    }

    private void loadCategory(){
        ArrayList<String> accType=new ArrayList<>();
        accType.add("Petrol");
        accType.add("Diesel");
        accType.add("Electric");
        accType.add("Hybrid");

        ObservableList dataSet = FXCollections.observableList(accType);
        comFuel.setItems(dataSet);
    }

    void setCellValueFactory() {
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colReg.setCellValueFactory(new PropertyValueFactory<>("reg"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colFuel.setCellValueFactory(new PropertyValueFactory<>("fuel"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("colour"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategory();
        setCellValueFactory();
        getAll();
    }

    void getAll() {
        try {
            ObservableList<Car> obList = FXCollections.observableArrayList();
            List<Car> cusList = CarModel.getAll();

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
    @FXML
    void setOnKeyPressed(MouseEvent event) throws SQLException {
        searchFilter(txtSearch);
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
                    } else if (Car.getStatus().toLowerCase().indexOf(searchKeyWord) > -1) {
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
}
