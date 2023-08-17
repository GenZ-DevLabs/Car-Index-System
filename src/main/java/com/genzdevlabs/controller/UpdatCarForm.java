package com.genzdevlabs.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdatCarForm {
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnUpdateCar;

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

    @FXML
    private AnchorPane updateVehiclePane;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }
}
