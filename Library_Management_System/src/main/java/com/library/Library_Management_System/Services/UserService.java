package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.User;

import java.util.List;

public interface UserService {
    List<User> saveAll(List<User> user);
    List<User> getAllUsers();
    User getUserById(int id);
    User updateUser(int id, User user);
    void deleteUser(int id);
}
