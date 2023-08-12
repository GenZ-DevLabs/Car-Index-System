package com.genzdevlabs.model;

import com.genzdevlabs.db.DataBaseConnection;
import com.genzdevlabs.dto.Car;
import com.genzdevlabs.dto.tm.CarTM;
import com.genzdevlabs.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarModel {
    public static List<Car> getAll() throws SQLException {
        Connection con = DataBaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM addcar";

        List<Car> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Car(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    public static boolean save(Car car) throws SQLException {
        String sql = "INSERT INTO addcar (brand, model, reg, year, fuel, capa, colour) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, car.getBrand(), car.getModel(), car.getReg(), car.getYear(), car.getFuel(), car.getCapacity(), car.getColour());
    }

//    public static boolean update(Item item) throws SQLException {
//        String sql="UPDATE item SET category = ?, description = ?, qty = ?, unit_price = ? WHERE item_id = ?";
//        return CrudUtil.execute(sql, item.getCategory(), item.getDescription(), item.getQty(), item.getUnit_price(), item.getItem_id());
//    }
//
//    public static boolean delete(String item) throws SQLException {
//        String sql="DELETE FROM item WHERE item_id = ?";
//        return CrudUtil.execute(sql, item);
//    }



//    public static boolean updateQty(List<CartDTO> cartDTOList) throws SQLException {
//        for (CartDTO dto : cartDTOList) {
//            if(!updateQty((List<CartDTO>) dto)) {
//                return false;
//            }
//        }
//        return true;
//    }
    public static Car searchById(String reg) throws SQLException {
        Connection con = DataBaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM caradd WHERE reg = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, reg);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Car(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }

    public static List<String> search() throws SQLException {
        Connection con = DataBaseConnection.getInstance().getConnection();

        ObservableList<String> observableList = FXCollections.observableArrayList();

        String sql = "SELECT model,brand,reg,year,fuel,capa,colour FROM addcar";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {

            String brand = resultSet.getString("model");
            String model = resultSet.getString("brand");
            String reg = resultSet.getString("reg");
            String year = resultSet.getString("year");
            String fuel = resultSet.getString("fuel");
            String capa = resultSet.getString("capa");
            String colour = resultSet.getString("colour");

            observableList.add(String.valueOf(new Car(brand,model,reg,year,fuel,capa,colour)));
        }
        return observableList;
    }
}
