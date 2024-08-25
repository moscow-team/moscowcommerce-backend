package com.example.moscowcommerce_backend.Users.Domain;

import org.springframework.stereotype.Repository;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByEmail(String email);
}
