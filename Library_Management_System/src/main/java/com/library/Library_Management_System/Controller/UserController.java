package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.Entity.User;
import com.library.Library_Management_System.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<List<User>> saveAll(@RequestBody List<User> users) {
        List<User> savedUsers = userService.saveAll(users);
        return ResponseEntity.status(HttpStatus.CREATED)
               .body(savedUsers);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public User updateUserById(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
