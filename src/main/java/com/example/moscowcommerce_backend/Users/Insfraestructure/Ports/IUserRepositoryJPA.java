package com.example.moscowcommerce_backend.Users.Insfraestructure.Ports;

import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepositoryJPA extends JpaRepository<UserEntity, Integer> {
  public Optional<UserEntity> findByEmail(String email);

  public Optional<UserEntity> findById(Integer id);
}
