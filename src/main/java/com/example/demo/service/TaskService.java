package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task){
        if(task.getTitle() == null || task.getTitle().trim().isEmpty()){
            return null;
        }
        if(task.getStatus() == null || task.getStatus().trim().isEmpty()){
            task.setStatus("pending");
        }
        return taskRepository.save(task);
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }
    //Editing tasks
    public Task updateTask(Long id, Task updatedTask){
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return null; 
        }
        if (updatedTask.getTitle() != null && !updatedTask.getTitle().trim().isEmpty()) {
            task.setTitle(updatedTask.getTitle());
        }
    
        if (updatedTask.getDescription() != null) {
            task.setDescription(updatedTask.getDescription());
        }
    
        if (updatedTask.getStatus() != null && !updatedTask.getStatus().trim().isEmpty()) {
            task.setStatus(updatedTask.getStatus());
        }
    
        if (updatedTask.getDueDate() != null) {
            task.setDueDate(updatedTask.getDueDate());
        }
    
        return taskRepository.save(task);
    }
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Task> getUpcomingTasks(Long userId) {
        return taskRepository.findByUserIdAndDueDateBetweenAndStatusNot(
            userId, LocalDate.now(), LocalDate.now().plusDays(3), "completed"
        );
    }
    public List<Task> getOverdueTasks(Long userId) {
        return taskRepository.findByUserIdAndDueDateBeforeAndStatusNot(
            userId, LocalDate.now(), "completed"
        );
    }
    public long countTasksByUserIdAndStatus(Long userId, String status) {
        return taskRepository.countByUserIdAndStatus(userId, status);
    }

}
