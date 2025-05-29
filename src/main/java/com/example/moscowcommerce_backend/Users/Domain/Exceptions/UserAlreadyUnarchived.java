package com.example.moscowcommerce_backend.Users.Domain.Exceptions;

public class UserAlreadyUnarchived extends RuntimeException {
  public UserAlreadyUnarchived(Integer id) {
    super("User with id " + id + " is already unarchived");
  }
}
