package com.example.demo6.service;

import com.example.demo6.dao.CustomerDAO;
import com.example.demo6.dto.Pageable;
import com.example.demo6.model.Customer;
import com.example.demo6.model.Role;

import java.util.ArrayList;
import java.util.List;

public class CustomerService implements BaseCRUDService<Customer>{

    private CustomerDAO customerDAO = new CustomerDAO();


    @Override
    public void create(Customer customer) {
        customerDAO.insertUser(customer);
    }

    @Override
    public void update(Customer customer) {
        customerDAO.updateUser(customer);
    }

    @Override
    public void delete(int id) {
        customerDAO.deleteUser(id);
    }

    @Override
    public Customer findById(int id) {
        return customerDAO.findById(id);
    }

    @Override
    public List<Customer> findAll(Pageable pageable) {
        //call xuong database => parse database tra ve thanh list object
        return customerDAO.findAll(pageable);
    }

    public Customer findByUsername(String username){
        return customerDAO.findByUsername(username);
    }
}
