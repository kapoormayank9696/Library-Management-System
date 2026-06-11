package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.User;
import com.library.Library_Management_System.Repository.UserRepository;
import com.library.Library_Management_System.Services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
