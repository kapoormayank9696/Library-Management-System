
package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.User;
import com.library.Library_Management_System.Repository.UserRepository;
import com.library.Library_Management_System.Services.UserService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    // Private Access Specifier And Data Members
    private final UserRepository userRepository;

    // Parameterized Constructor
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // ---------------- ADD USER ----------------------
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // ---------------- GET ALL USERS -----------------
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ---------------- GET USER BY ID ----------------
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + id));
    }

    // ---------------- UPDATE USER BY ID--------------
    @Override
    public User updateUser(int id, User user) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + id));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    // ---------------- DELETE USER BY ID--------------
    @Override
    public void deleteUser(int id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + id));

        userRepository.delete(existingUser);
    }
}
