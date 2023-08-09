package com.genzdevlabs.controller;

import com.genzdevlabs.dto.Car;
import com.genzdevlabs.model.CarModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.genzdevlabs.model.CarModel.getAll;


public class AddCarScreenForm implements Initializable {
    @FXML
    private JFXButton btnAddCar;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXComboBox<?> comFuel;

    @FXML
    private TextField textCapacity;

    @FXML
    private TextField textColour;

    @FXML
    private TextField textReg;

    @FXML
    private TextField textYear;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtModel;

    private void loadCategory(){
        ArrayList<String> accType=new ArrayList<>();
        accType.add("Petrol");
        accType.add("Diesel");
        accType.add("Electric");
        accType.add("Hybrid");

        ObservableList dataSet = FXCollections.observableList(accType);
        comFuel.setItems(dataSet);
    }



    @FXML
    void btnAddOnAction(ActionEvent event) {
        String brand = txtBrand.getText();
        String model = txtModel.getText();
        String reg = textReg.getText();
        String year = textYear.getText();
        String fueltype = (String) comFuel.getValue();
        String capacity = textCapacity.getText();
        String colour = textColour.getText();
        var customer = new Car(brand, model, reg, year, fueltype, capacity, colour);

        try {
            boolean isSaved = CarModel.save(customer);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "CAR Added Successfully").show();
                getAll();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategory();
    }


}
