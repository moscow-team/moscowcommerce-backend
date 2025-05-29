package com.example.moscowcommerce_backend.Users.Insfraestructure.DTO;

import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ResultUserDTO {
  private Integer id;
  private String email;
  private String fullName;
  private Role role;
  private LocalDate creationDate;
  private LocalDate modificationDate;
  private LocalDate archivedDate;

  private ResultUserDTO(
      Integer id,
      String email,
      String fullName,
      Role role,
      LocalDate creationDate,
      LocalDate modificationDate,
      LocalDate archivedDate) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.role = role;
    this.creationDate = creationDate;
    this.modificationDate = modificationDate;
    this.archivedDate = archivedDate;
  }

  public static ResultUserDTO create(
      Integer id,
      String email,
      String fullName,
      Role role,
      LocalDate creationDate,
      LocalDate modificationDate,
      LocalDate archivedDate) {
    return new ResultUserDTO(
        id, email, fullName, role, creationDate, modificationDate, archivedDate);
  }
}
