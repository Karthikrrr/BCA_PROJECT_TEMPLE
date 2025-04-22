package com.business_website.service_implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.business_website.WebsiteApplication;
import com.business_website.dto.UserDto;
import com.business_website.models.User;
import com.business_website.repository.UserRepo;
import com.business_website.services.UserService;


@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(WebsiteApplication.class);


    @Override
    public User save(UserDto userDto) {
       userDto.setCreatedAt(LocalDateTime.now());
       userDto.setRole("USER");
       User user = new User(
                    userDto.getEmail(),
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getCreatedAt(), 
                    userDto.getRole());
                    
        return userRepo.save(user);
    }
    
    @Override
    public boolean checkUsername(String email){
        return userRepo.checkUsername(email);
    }

    @Override
    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        userRepo.delete(user);
    }

    @Bean
    public void defaultUser(){
        if(userRepo.checkUsername("admin@gmail.com")){
            logger.info("ADMIN Already Exist!!!!");
        }
        else{
            User admin = new User("admin@gmail.com", passwordEncoder.encode("123123"), LocalDateTime.now(), "ADMIN");
            userRepo.save(admin);
            logger.info("ADMIN Add Sucessfully!!!!");
        }
    }
}
