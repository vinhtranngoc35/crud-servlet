package com.example.demo6.dao;

import com.example.demo6.model.Customer;
import com.example.demo6.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    //                                                     database
    private String jdbcURL = "jdbc:mysql://localhost:3306/baitap";
    private String jdbcUsername = "root";
    //                            password của mình
    private String jdbcPassword = "123123";

    private final String SELECT_USERS = "SELECT customers.*, roles.`name` as role_name " +
            "FROM customers LEFT JOIN roles " +
            "ON customers.role_id = roles.id;";
    private final String SELECT_USERS_BY_ID = "SELECT customers.*, roles.`name` as role_name " +
            "FROM customers LEFT JOIN roles " +
            "ON customers.role_id = " +
            "roles.id where customers.id = ?;";

    private final String INSERT_USER = "INSERT INTO `customers` (`name`, `email`, `role_id`) " +
            "VALUES (?, ?, ?);";

    private final String UPDATE_USER = "UPDATE `customers` " +
            "SET `name` = ?, `email` = ?, role_id = ? WHERE (`id` = ?);";

    private final String DELETE_USER = "DELETE FROM `customers` WHERE (`id` = ?);";

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

    public List<Customer> findAll() {
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
                String roleName = rs.getString("role_name");
                int roleId = rs.getInt("role_id");
                customers.add(new Customer(id, name, email, new Role(roleId, roleName)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public Customer findById(int id) {
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
                String roleName = rs.getString("role_name");
                int roleId = rs.getInt("role_id");
                return new Customer(idCus, name, email, new Role(roleId, roleName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void insertUser(Customer customer) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setInt(3, customer.getRole().getId());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(Customer customer) {

        try (Connection connection = getConnection();
             //UPDATE `customers` " +
             //            "SET `name` = ?, `email` = ?, role_id = ? WHERE (`id` = ?);";
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setInt(3, customer.getRole().getId());
            preparedStatement.setInt(4, customer.getId());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(int id) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
