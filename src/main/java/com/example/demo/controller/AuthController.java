package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.util.Collections;


import jakarta.servlet.http.HttpSession;



//Handles log-in sessions
@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestBody User loginRequest,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) {

        User user = userService.login(loginRequest);

        if (user != null) {

            session.setAttribute("user", user);

         
            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(auth);

           
            HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
            repo.saveContext(SecurityContextHolder.getContext(), request, new HttpServletResponseWrapper(response));

            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return (user != null)
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
