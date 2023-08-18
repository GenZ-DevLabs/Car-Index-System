package com.genzdevlabs.controller;

import com.genzdevlabs.dto.Car;
import com.genzdevlabs.dto.tm.OrderTM;
import com.genzdevlabs.model.CarModel;
import com.genzdevlabs.model.OrderModel;
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

public class OrderDetailFormController implements Initializable {

    @FXML
    private TableView<OrderTM> tblOrderDetails;

    @FXML
    private TableColumn<?, ?> colOid;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colReg;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colColour;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TextField txtSearch;

    @FXML
    void SearchOnMouseClick(MouseEvent event) throws SQLException {
        searchFilter(txtSearch);
    }

    void setCellValueFactory() {
        colOid.setCellValueFactory(new PropertyValueFactory<>("oid"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colReg.setCellValueFactory(new PropertyValueFactory<>("reg"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("colour"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    void getAll() {
        try {
            ObservableList<OrderTM> obList = FXCollections.observableArrayList();
            List<OrderTM> cusList = OrderModel.getAll();

            for (OrderTM orderTM : cusList) {
                obList.add(new OrderTM(
                        orderTM.getOid(),
                        orderTM.getNic(),
                        orderTM.getReg(),
                        orderTM.getName(),
                        orderTM.getBrand(),
                        orderTM.getModel(),
                        orderTM.getColour(),
                        orderTM.getDate()
                ));
            }
            tblOrderDetails.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Loading Error!!!").show();
        }
    }

    public void searchFilter(TextField search) throws SQLException {
        FilteredList<OrderTM> filteredData = new FilteredList<>(tblOrderDetails.getItems(), b -> true);

        search.setOnKeyPressed(keyEvent -> {
            search.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate(OrderTM -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    if (OrderTM.getOid().toLowerCase().indexOf(searchKeyWord)>-1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }));

            SortedList<OrderTM> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(tblOrderDetails.comparatorProperty());
            tblOrderDetails.setItems(sortedList);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }
}
