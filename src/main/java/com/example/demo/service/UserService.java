package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User register(User user){
        if(user.getUsername() == null || user.getPassword() == null){
            return null;
        }
        if(userRepository.existsByUsername(user.getUsername())){
            return null;
        }
        
        user.setRole("user");
        
        return userRepository.save(user);
    }

    public User login(User user){
        if (user.getUsername() != null && user.getPassword() != null) {
            Optional<User> existingUserOpt = userRepository.findByUsername(user.getUsername());
    
            if (existingUserOpt.isPresent()) {
                User existingUser = existingUserOpt.get();
                if (existingUser.getPassword().equals(user.getPassword())) {
                    return existingUser; // Login successful
                }
            }
        }
        return null; // Login failed
    }

    public boolean updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return false;

        user.setPassword(newPassword); 
        userRepository.save(user);
        return true;
    }

    //Admin
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            taskRepository.deleteByUserId(id);
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
