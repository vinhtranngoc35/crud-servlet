package com.example.demo6.controller;

import com.example.demo6.model.Customer;
import com.example.demo6.service.CustomerService;
import com.example.demo6.ultils.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CustomerService customerService = new CustomerService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Customer customer = customerService.findByUsername(username);
            if(customer != null && PasswordEncoder.check(password, customer.getPassword())){
                HttpSession session = request.getSession();
                session.setAttribute("role", customer.getRole().getName());
                response.sendRedirect("admin/customers");
                return;
            }
            request.setAttribute("errors", "Login Failed");
            request.getRequestDispatcher("index.jsp")
                    .forward(request,response);

    }
}