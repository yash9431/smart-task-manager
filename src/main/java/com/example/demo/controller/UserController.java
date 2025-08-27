package com.example.demo.controller;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Login function
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest, HttpSession session) {
        User user = userService.login(loginRequest);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        session.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return (user != null)
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<Boolean> updatePassword(@PathVariable Long id, @RequestBody User user) {
        boolean updated = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(updated);
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

