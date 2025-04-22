package com.business_website.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.business_website.models.User;
import com.business_website.service_implementation.UserServiceImplementation;

@Controller
public class AdminController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserServiceImplementation userServiceImplementation;


     @GetMapping("/admin")
    public String adminAndUserList(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<User> users = userServiceImplementation.getAllUsers();
        model.addAttribute("userList" , users);
        model.addAttribute("users", userDetails);
        return "admin/admin";
    }


    @GetMapping("/admin/users/confirmDelete/{id}")
    public String confirmDelete(@PathVariable("id") Long id, Model model){
        model.addAttribute("users", id);
        return "admin/confirm-user-delete";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userServiceImplementation.deleteUser(id);
        return "redirect:/admin";
    }
    
}

