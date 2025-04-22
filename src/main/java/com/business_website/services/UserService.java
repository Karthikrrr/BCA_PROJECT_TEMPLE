package com.business_website.services;

import java.util.List;

import com.business_website.dto.UserDto;
import com.business_website.models.User;

public interface UserService {
    List<User> getAllUsers();
    User save(UserDto userDto);
    boolean checkUsername(String email);
    void deleteUser(Long id);
}
