package com.ecommerce.ecommerce.auth.repository;


import com.ecommerce.ecommerce.auth.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // Add other custom queries if needed
}


