package com.ecommerce.ecommerce.auth.service;

// UserService.java
// UserService.java

import com.ecommerce.ecommerce.auth.modal.User;
import com.ecommerce.ecommerce.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Check if the username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        // Update user details
        existingUser.setUsername(updatedUser.getUsername());
        // Update other user details as needed

        // Save the updated user to the database
        return userRepository.save(existingUser);
    }

    // Implement other user-related methods like updating user details, changing password, etc.
}
