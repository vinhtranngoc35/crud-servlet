package com.example.demo6.dao;

import com.example.demo6.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    //                                                     database
    private String jdbcURL = "jdbc:mysql://localhost:3306/baitap";
    private String jdbcUsername = "root";
    //                            password của mình
    private String jdbcPassword = "123123";

    private final String SELECT_USERS = "SELECT * FROM customers";
    private final String SELECT_USERS_BY_ID = "SELECT * FROM customers WHERE id = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public List<Customer> findAll(){
        List<Customer> customers = new ArrayList<>();
        // Step 1: tạo 1 kết nối xuống db để gọi câu lệnh SELECT or UPDATE, Delete, vv
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: tương đương vowis cái sét
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4:
            //kiểm tra còn data hay không. còn thì cứ lấy bằng câu lệnh ở dưới
            while (rs.next()) {
                //(truyên vào tên cột)
                int id = rs.getInt("id");
                //(truyên vào tên cột)
                String name = rs.getString("name");
                //(truyên vào tên cột)
                String email = rs.getString("email");
                customers.add(new Customer(id, name, email));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }
    public Customer findById(int id){
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_USERS_BY_ID);) {
            System.out.println(preparedStatement);
            preparedStatement.setInt(1, id);

            // Step 3: tương đương vowis cái sét
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4:
            //kiểm tra còn data hay không. còn thì cứ lấy bằng câu lệnh ở dưới
            while (rs.next()) {
                //(truyên vào tên cột)
                int idCus = rs.getInt("id");
                //(truyên vào tên cột)
                String name = rs.getString("name");
                //(truyên vào tên cột)
                String email = rs.getString("email");
                return new Customer(idCus, name, email);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
