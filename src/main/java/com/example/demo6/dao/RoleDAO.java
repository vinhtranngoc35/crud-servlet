package com.example.demo6.dao;

import com.example.demo6.model.Customer;
import com.example.demo6.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    //                                                     database
    private String jdbcURL = "jdbc:mysql://localhost:3306/baitap";
    private String jdbcUsername = "root";
    //                            password của mình
    private String jdbcPassword = "123123";

    private final String SELECT_ROLES = "SELECT * FROM roles";
    private final String SELECT_ROLES_BY_ID = "SELECT * FROM roles WHERE id = ?";
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

    public List<Role> findAll(){
        List<Role> roles = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_ROLES)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                roles.add(new Role(id, name));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return roles;
    }
    public Role findById(int id) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_ROLES_BY_ID);) {
            System.out.println(preparedStatement);
            preparedStatement.setInt(1, id);

            // Step 3: tương đương vowis cái sét
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4:
            //kiểm tra còn data hay không. còn thì cứ lấy bằng câu lệnh ở dưới
            while (rs.next()) {
                int idCus = rs.getInt("id");
                String name = rs.getString("name");

                return new Role(idCus, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
