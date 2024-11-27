package com.example.moscowcommerce_backend.Users.Insfraestructure.Entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "users")
public final class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, columnDefinition = "DATE", updatable = false)
    private LocalDate creationDate;

    @Column(nullable = true, columnDefinition = "DATE")
    private LocalDate modificationDate;

    @Column(nullable = true, columnDefinition = "DATE")
    private LocalDate archivedDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modificationDate = LocalDate.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}