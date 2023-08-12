package com.genzdevlabs.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddCustomerForm {
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnSellCar;

    @FXML
    private AnchorPane customerDetailsPane;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textNIC;

    @FXML
    private TextField textName;

    @FXML
    private TextField textPhoneNumber;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnSellCarOnAction(ActionEvent event) {

    }
}
