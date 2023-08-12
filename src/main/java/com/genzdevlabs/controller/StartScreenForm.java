package com.genzdevlabs.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenForm {
    @FXML
    private AnchorPane addVehiclePane;

    @FXML
    private JFXButton btnAddCar;

    @FXML
    private JFXButton btnSearchCar;

    @FXML
    private JFXButton btnShowAll;

    @FXML
    void btnAddCarOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/AddCarScreenForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("Add Cars");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnSearchCarOnAction(ActionEvent event) {

    }

    @FXML
    void btnShowAllOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/ShowAllForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("Show All Cars");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
