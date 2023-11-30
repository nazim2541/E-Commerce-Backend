package com.ecommerce.ecommerce.auth.controller;

// UserController.java

import com.ecommerce.ecommerce.auth.modal.User;
import com.ecommerce.ecommerce.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Implement logic to retrieve user by ID from the service
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        // Implement logic to update user by ID from the service
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    // Add other user-related endpoints as needed
}

