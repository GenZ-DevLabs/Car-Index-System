package com.genzdevlabs.controller;

import com.genzdevlabs.dto.Car;
import com.genzdevlabs.model.CarModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllFormController implements Initializable {

    @FXML
    private TableView<Car> tblShowAll;

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
    private TextField txtSearch;


    void setCellValueFactory() {
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colReg.setCellValueFactory(new PropertyValueFactory<>("reg"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colFuel.setCellValueFactory(new PropertyValueFactory<>("fuel"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("colour"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                        car.getColour()
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
