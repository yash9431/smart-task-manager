package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //Task title
    private String description; 
    private String status;
    private LocalDate creationDate;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // --- Constructors ---
    public Task() {}

    //Not needed. Spring boot auto-constructs with get/setters.
    public Task(String title, String description, String status, LocalDate creationDate, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getStatus(){
        return status;
    }
    public LocalDate getDueDate(){
        return dueDate;
    }
    public LocalDate getCreationDate(){
        return creationDate;
    }
    public User getUser() {
        return user;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setUser(User user) {
        this.user = user;
    }
}   
