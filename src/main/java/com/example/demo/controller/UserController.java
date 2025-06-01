package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Helper function for updatePassword
    public static class PasswordUpdateDTO {
        private String newPassword;

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.register(user);
        if (newUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
        return ResponseEntity.ok(newUser);
    }

    // User can change password
    @PutMapping("/{id}/password")
    public boolean updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO dto) {
        return userService.updatePassword(id, dto.getNewPassword());
    }

    // Delete own account
    @DeleteMapping("/me/{id}")
    public ResponseEntity<?> deleteOwnAccount(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Get all users (Admin only)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID (Admin)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Delete user by ID (Admin)
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}

