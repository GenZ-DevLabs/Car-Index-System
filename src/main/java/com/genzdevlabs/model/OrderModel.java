package com.genzdevlabs.model;

import com.genzdevlabs.db.DataBaseConnection;
import com.genzdevlabs.dto.Car;
import com.genzdevlabs.dto.Customer;
import com.genzdevlabs.dto.tm.OrderTM;
import com.genzdevlabs.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static List<OrderTM> getAll() throws SQLException {
        Connection con = DataBaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orders";

        List<OrderTM> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new OrderTM(
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

    public static boolean save(OrderTM order) throws SQLException {
        String sql = "INSERT INTO orders (oid, nic, reg, name, brand, model, colour, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, order.getOid(), order.getNic(), order.getReg(), order.getName(), order.getBrand(), order.getModel(), order.getColour(), order.getDate());
    }

    public static boolean updateStatus(String reg) throws SQLException {
        String sql = "UPDATE addcar SET status='Sold' WHERE reg = ?";
        return CrudUtil.execute(sql, reg);
    }

//    public static boolean update(Customer customer) throws SQLException {
//        String sql="UPDATE customer SET name = ?, mobile = ?, email = ?, address = ? WHERE nic = ?";
//        return CrudUtil.execute(sql, customer.getName(), customer.getMobile(), customer.getEmail(), customer.getAddress(), customer.getNic());
//    }
//
//    public static boolean delete(String customer) throws SQLException {
//        String sql="DELETE FROM customer WHERE nic = ?";
//        return CrudUtil.execute(sql, customer);
//    }



    //    public static boolean updateQty(List<CartDTO> cartDTOList) throws SQLException {
//        for (CartDTO dto : cartDTOList) {
//            if(!updateQty((List<CartDTO>) dto)) {
//                return false;
//            }
//        }
//        return true;
//    }
//    public static Car searchById(String nic) throws SQLException {
//        Connection con = DataBaseConnection.getInstance().getConnection();
//        String sql = "SELECT * FROM customer WHERE nic = ?";
//
//        PreparedStatement pstm = con.prepareStatement(sql);
//        pstm.setString(1, nic);
//
//        ResultSet resultSet = pstm.executeQuery();
//        if(resultSet.next()) {
//            return new Car(
//                    resultSet.getString(1),
//                    resultSet.getString(2),
//                    resultSet.getString(3),
//                    resultSet.getString(4),
//                    resultSet.getString(5),
//                    resultSet.getString(6),
//                    resultSet.getString(7),
//                    resultSet.getString(8)
//            );
//        }
//        return null;
//    }

    public static String generateNextOrderId() throws SQLException {
        Connection con = DataBaseConnection.getInstance().getConnection();

        String sql = "SELECT oid FROM orders ORDER BY oid DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("O");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "O00"+id;
        }
        return "O001";
    }
}
