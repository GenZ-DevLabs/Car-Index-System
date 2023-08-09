package com.genzdevlabs.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddCarScreenForm {
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

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }
}
