package com.example.demo6.controller;

import com.example.demo6.dto.Pageable;
import com.example.demo6.model.Customer;
import com.example.demo6.model.Role;
import com.example.demo6.service.CustomerService;
import com.example.demo6.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "customerServlet", urlPatterns = "/admin/customers")
public class CustomerServlet extends HttpServlet {

    private int TOTAL_ITEMS = 5;
    private CustomerService customerService = new CustomerService();
    private RoleService roleService = new RoleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                createCustomer(req, resp);
                break;
            case "edit":
                editCustomer(req,resp);
                break;
            default:
                showCustomer(req,resp);
        }
    }

    private void editCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        int roleId = Integer.parseInt(req.getParameter("role"));

        Role role = roleService.findById(roleId);
        Customer customer = new Customer(id,name,email, role);
        customerService.update(customer);

        req.setAttribute("customer",customer);
        req.setAttribute("roles", roleService.findAll());
        req.setAttribute("message", "edited");
        req.getRequestDispatcher("edit.jsp").forward(req,resp);
    }


    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String mail = req.getParameter("email");
        int roleId = Integer.parseInt(req.getParameter("role"));

        Role role = roleService.findById(roleId);
        Customer customer = new Customer(name, mail, role);
        customerService.create(customer);

        req.setAttribute("customer", customer);
        req.setAttribute("message", "Created");
        req.setAttribute("roles", roleService.findAll());
        req.getRequestDispatcher("create.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                showFormCreateCustomer(req, resp);
                break;
            case "edit":
                showEditCustomer(req, resp);
                break;
            case "delete":
                deleteCustomer(req, resp);
                break;
            default:
                showCustomer(req,resp);
        }
    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        customerService.delete(id);
        showCustomer(req, resp);
    }

    private void showEditCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        // lấy customer có id bằng với id trong parameter;
        // gửi customer qua bên edit.jsp;
        // điều hướng tới trang edit.jsp;
        Customer customer = customerService.findById(id);
        req.setAttribute("customer", customer);
        req.setAttribute("roles", roleService.findAll());
        req.getRequestDispatcher("edit.jsp")
                .forward(req, resp);
    }

    private void showFormCreateCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", roleService.findAll());
        req.getRequestDispatcher("create.jsp")
                .forward(req,resp);
    }

    private void showCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");

        int page = 1;
        if(req.getParameter("page") != null){
            page = Integer.parseInt(req.getParameter("page"));
        }
        String sortBy = req.getParameter("sortby");
        if(sortBy == null){
            sortBy = "asc";
        }
        String nameField = req.getParameter("nameField");
        if(nameField == null){
            nameField = "customers.id";
        }
        Pageable pageable = new Pageable(search, page, TOTAL_ITEMS , nameField, sortBy);
        req.setAttribute("customers",customerService.findAll(pageable));
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("demo.jsp").forward(req,resp);
    }

}
