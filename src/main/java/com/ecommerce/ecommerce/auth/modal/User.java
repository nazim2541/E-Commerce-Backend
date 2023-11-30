package com.ecommerce.ecommerce.auth.modal;

// User.java


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    // Other user details

    // Implement UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can implement role-based access if needed
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement account expiration logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement account locking logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement credential expiration logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement account enabling logic if needed
    }
}
