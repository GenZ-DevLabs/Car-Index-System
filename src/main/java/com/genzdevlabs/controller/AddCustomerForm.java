package com.genzdevlabs.controller;

import com.genzdevlabs.dto.Customer;
import com.genzdevlabs.model.CustomerModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.genzdevlabs.model.CarModel.getAll;

public class AddCustomerForm implements Initializable {
    @FXML
    private JFXButton btnAddCustomer;

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
    void AddCustomerOnAction(ActionEvent event) {
        String nic = textNIC.getText();
        String name = textName.getText();
        String mobile = textPhoneNumber.getText();
        String email = textEmail.getText();
        String address = textAddress.getText();
        var customer = new Customer(nic, name, mobile, email, address);

        try {
            boolean isSaved = CustomerModel.save(customer);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer added successfully").show();
                getAll();

                textNIC.setText("");
                textName.setText("");
                textPhoneNumber.setText("");
                textEmail.setText("");
                textAddress.setText("");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
