package com.business_website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.business_website.dto.UserDto;
import com.business_website.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    // @Autowired
    // UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    
    @GetMapping("/")
    public String getHome(Model model){        
        return "users/home";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("users", new UserDto());
        return "users/register";
    }

    @PostMapping("/register")
        public String saveUser(@Valid @ModelAttribute("users") UserDto userDto, Model model, @RequestParam("confirmPassword") String confirmPassword){
            if(userService.checkUsername(userDto.getEmail())){
                model.addAttribute("message", "Email Alredy exist");
                return "users/register";              
            }
            else if(!confirmPassword.equals(userDto.getPassword())){
                model.addAttribute("message", "Password Didnt Match Re-Enter Password");
                return "users/register";
            }
            else{
                userService.save(userDto);
                model.addAttribute("message", "Register Sucessfull");
                return "users/login";
            }   
    }

    @GetMapping("/login")
    public String login(){
        return "users/login";
    }

    @GetMapping("/about")
    public String aboutUs(){
        return "users/about";
    }

    @GetMapping("/contact")
    public String contactUs(){
        return "users/contact";
    }

    @GetMapping("/collabrations")
    public String collabrations(){
        return "users/collabrations";
    }
}
