package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.TaskService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Creates task and assigns to requested user
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, HttpSession session) {
    
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    
        task.setUser(user);
        task.setCreationDate(LocalDate.now());
    
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.ok(savedTask);
    }

    // Get all tasks (or maybe filter by user in the future)
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    // Get task by ID
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUserId(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    // Update task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    // Delete task
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    //Methods below for dashboard display
    @GetMapping("/upcoming/{userId}")
    public List<Task> getUpcomingTasks(@PathVariable Long userId) {
        return taskService.getUpcomingTasks(userId);
    }

    @GetMapping("/overdue/{userId}")
    public List<Task> getOverdueTasks(@PathVariable Long userId) {
        return taskService.getOverdueTasks(userId);
    }

    @GetMapping("/count/{userId}/{status}")
    public long countTasksByStatus(@PathVariable Long userId, @PathVariable String status) {
        return taskService.countTasksByUserIdAndStatus(userId, status);
    }

}
