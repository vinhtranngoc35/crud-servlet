package com.example.demo6.service;

import com.example.demo6.model.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleService {
    static List<Role> roles;
    static {
        roles = new ArrayList<>();
        roles.add(new Role(1, "Tutor"));
        roles.add(new Role(2, "Intructor"));
        roles.add(new Role(3, "Student"));
    }

    public List<Role> findAll(){
        return roles;
    }

    public Role findById(int id){
        for (Role role: roles) {
            if(role.getId() == id){
                return role;
            }
        }
        return null;
    }
}
