package com.example.demo6.service;

import com.example.demo6.dao.RoleDAO;
import com.example.demo6.model.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleService {
    private RoleDAO roleDAO = new RoleDAO();

    public List<Role> findAll(){
        return roleDAO.findAll();
    }

    public Role findById(int id){

        return roleDAO.findById(id);
    }
}
