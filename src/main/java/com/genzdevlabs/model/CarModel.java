package com.genzdevlabs.model;

import com.genzdevlabs.db.DataBaseConnection;
import com.genzdevlabs.dto.Car;
import com.genzdevlabs.util.CrudUtil;

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
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return data;
    }

    public static List<Car> getAllUnSold() throws SQLException {
        Connection con = DataBaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM addcar where status= 'UnSold'";

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
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return data;
    }

    public static boolean save(Car car) throws SQLException {
        String sql = "INSERT INTO addcar (brand, model, reg, year, fuel, capa, colour, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, car.getBrand(), car.getModel(), car.getReg(), car.getYear(), car.getFuel(), car.getCapacity(), car.getColour(), car.getStatus());
    }

    public static boolean update(Car car) throws SQLException {
        String sql="UPDATE addcar SET brand = ?, model = ?, year = ?, fuel = ?, capa = ?, colour = ?, status = ? WHERE reg = ?";
        return CrudUtil.execute(sql, car.getBrand(), car.getModel(), car.getYear(), car.getFuel(), car.getCapacity(), car.getColour(), car.getStatus(), car.getReg());
    }

    public static boolean delete(String car) throws SQLException {
        String sql="DELETE FROM addcar WHERE reg = ?";
        return CrudUtil.execute(sql, car);
    }



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
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
        }
        return null;
    }
}
