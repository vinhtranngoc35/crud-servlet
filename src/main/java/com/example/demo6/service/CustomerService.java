package com.example.demo6.service;

import com.example.demo6.dao.CustomerDAO;
import com.example.demo6.model.Customer;
import com.example.demo6.model.Role;

import java.util.ArrayList;
import java.util.List;

public class CustomerService implements BaseCRUDService<Customer>{
    private static List<Customer> customers;

    private CustomerDAO customerDAO = new CustomerDAO();

    private static int currentId;

    static {
        customers = new ArrayList<>();
        customers.add(new Customer(++currentId, "Vinh", "vinh.tran2@gmail.com", new Role(1, "Tutor")));
        customers.add(new Customer(++currentId, "Phuc", "phuc.tran2@gmail.com", new Role(1, "Tutor")));
    }

    @Override
    public void create(Customer customer) {
        customer.setId(++currentId);
        customers.add(customer);
    }

    @Override
    public void update(Customer customer) {
        for (Customer item : customers) {
            if (item.getId() == customer.getId()) {
                item.setName(customer.getName());
                item.setEmail(customer.getEmail());
                item.setRole(customer.getRole());
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i =0; i < customers.size(); i++){
            if(customers.get(i).getId() == id){
                customers.remove(i);
                break;
            }
        }
    }

    @Override
    public Customer findById(int id) {
        return customerDAO.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        //call xuong database => parse database tra ve thanh list object
        return customerDAO.findAll();
    }
}
