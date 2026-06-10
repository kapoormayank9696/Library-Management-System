package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    void deleteUser(Long id);
}
