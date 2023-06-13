package com.example.demo6.dao;

import com.example.demo6.dto.Pageable;
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
            "ON customers.role_id = roles.id WHERE\n" +
            "    lower(customers.`name`) LIKE ? OR lower(customers.email) LIKE ? \n" +
            "        OR lower(roles.`name`) LIKE ? ORDER BY ? ?  LIMIT ? OFFSET ? ;";
    private final String SELECT_USERS_BY_ID = "SELECT customers.*, roles.`name` as role_name " +
            "FROM customers LEFT JOIN roles " +
            "ON customers.role_id = " +
            "roles.id where customers.id = ?;";

    private final String SELECT_USER_BY_USERNAME = "SELECT customers.*, roles.`name` as role_name " +
            "FROM customers LEFT JOIN roles " +
            "ON customers.role_id = " +
            "roles.id where customers.username = ?;";

    private final String INSERT_USER = "INSERT INTO `customers` (`name`, `email`, `role_id`, `username`, `password`) " +
            "VALUES (?, ?, ?, ?, ?);";

    private final String UPDATE_USER = "UPDATE `customers` " +
            "SET `name` = ?, `email` = ?, role_id = ? WHERE (`id` = ?);";

    private final String DELETE_USER = "DELETE FROM `customers` WHERE (`id` = ?);";

    private final String TOTAL_USERS = "SELECT \n" +
            "    COUNT(1) as total \n" +
            "FROM\n" +
            "    customers\n" +
            "        LEFT JOIN\n" +
            "    roles ON customers.role_id = roles.id\n" +
            "WHERE\n" +
            "    lower(customers.`name`) LIKE ? OR lower(customers.email) LIKE ?\n" +
            "        OR lower(roles.`name`) LIKE ? ;";

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

    private String SELECT_ALL_USERS = "SELECT \n" +
            "    customers.*, roles.`name` as role_name \n" +
            "FROM\n" +
            "    customers\n" +
            "        LEFT JOIN\n" +
            "    roles ON customers.role_id = roles.id\n" +
            "WHERE\n" +
            "    lower(customers.`name`) LIKE '%s' OR lower(customers.email) LIKE '%s' \n" +
            "        OR lower(roles.`name`) LIKE '%s' order by %s %s LIMIT %d OFFSET %d  ;\n";

    public List<Customer> findAll(Pageable pageable) {
        List<Customer> customers = new ArrayList<>();
        String search = pageable.getSearch();
        if(search == null){
            search = "";
        }
        search = "%" + search + "%";
        // Step 1: tạo 1 kết nối xuống db để gọi câu lệnh SELECT or UPDATE, Delete, vv
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(String
                             .format(SELECT_ALL_USERS, search, search, search,
                                     pageable.getNameField(), pageable.getSortBy(),
                                     pageable.getTotalItems(),(pageable.getPage() - 1) * pageable.getTotalItems()))) {
            System.out.println(preparedStatement);
//            preparedStatement.setString(1, search);
//            preparedStatement.setString(2, search);
//            preparedStatement.setString(3, search);
//            preparedStatement.setInt(6, pageable.getTotalItems());
//            preparedStatement.setInt(7, (pageable.getPage() - 1) * pageable.getTotalItems());
//            preparedStatement.setString(4, pageable.getNameField());
//            preparedStatement.setString(5, pageable.getSortBy());

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

            // cụm lấy tổng số trang
            PreparedStatement statementTotalUsers =connection.prepareStatement(TOTAL_USERS);
            statementTotalUsers.setString(1, search);
            statementTotalUsers.setString(2, search);
            statementTotalUsers.setString(3, search);
           ResultSet rsTotalUser = statementTotalUsers.executeQuery();
           while (rsTotalUser.next()){
               double totalUsers = rsTotalUser.getDouble("total");
               double totalItems = Double.parseDouble(pageable.getTotalItems() + "");
               int totalPages = (int)
                       Math.ceil(totalUsers/totalItems);
               pageable.setTotalPage(totalPages);
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
    public Customer findByUsername(String username) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_USER_BY_USERNAME);) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1, username);

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
                String password = rs.getString("password");
                int roleId = rs.getInt("role_id");
                return new Customer(idCus, name, email, password, username, new Role(roleId, roleName));
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
            preparedStatement.setString(4, customer.getUsername());
            preparedStatement.setString(5,customer.getPassword());
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
