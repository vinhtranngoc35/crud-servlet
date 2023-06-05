package com.example.demo6;

import com.example.demo6.model.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
    private String message;

    private List<Customer> customers = new ArrayList<>();

    private int idCurrent = 2;

    public void init() {
        message = "Hello World!";
        customers.add(new Customer(1,"Vinh","vinh@email.com"));
        customers.add(new Customer(2,"Hung","hung@email.com"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       String action = request.getParameter("action");
       //http://localhost:8080/demo6_war_exploded/customers?action=add
        if(action == null){
            action = "";
        }
       request.setAttribute("action", action);
       switch (action){
           case "add":
               showCreateCustomer(request,response);
               break;
           case"edit":
               showEditCustomer(request,response);
               break;
           case "delete":
               delete(request,response);
               break;
           default:
               showCustomers(request,response);
       }

    }

    private void showEditCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
//        Customer customer = customers.
//                stream().filter(e -> e.getId() == id)
//                .findFirst().orElse(null);
        for (Customer customer : customers) {
            if (customer.getId() == id){
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("edit.jsp").forward(request,response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String action = req.getParameter("action");
       switch (action){
           case "add":
               createCustomer(req,resp);
               break;
           case"edit":
               editCustomer(req,resp);
               break;

       }
    }

    private void editCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        for (Customer customer:customers) {
            if (customer.getId() == id){
                customer.setName(name);
                customer.setEmail(email);
                break;
            }
        }
        message = "sửa thành công";
        req.setAttribute("message",message);
        showCustomers(req,resp);
    }

    private  void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        customers = customers
                .stream().filter(e ->e.getId() != id)
                .collect(Collectors.toList());

        showCustomers(req,resp);
    }

    public void showCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        customerList = customerList.stream()
//                .filter(e -> e.getId() != id))
//                .collect(Collectors.toList());
        //gui du lieu qua
        request.setAttribute("demo",customers);
        // gui qua trang nao
        request.getRequestDispatcher("demo.jsp").forward(request,response);
    }
    public void showCreateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("create.jsp")
                .forward(request,response);

    }


    public void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        customers.add(new Customer(++idCurrent,name,email));
        message = "Them thanh cong";
        req.setAttribute("message", message);
        req.getRequestDispatcher("create.jsp")
                .forward(req,resp);
    }
}