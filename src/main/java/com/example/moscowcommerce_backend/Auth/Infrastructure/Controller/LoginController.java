package com.example.moscowcommerce_backend.Auth.Infrastructure.Controller;

import com.example.moscowcommerce_backend.Auth.Application.AuthService;
import com.example.moscowcommerce_backend.Auth.Domain.Exceptions.UserArchivedException;
import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.LogInDTO;
import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.ResultLogInDTO;
import com.example.moscowcommerce_backend.Auth.Infrastructure.Mappers.LogInMapper;
import com.example.moscowcommerce_backend.Auth.Infrastructure.Security.JwtService;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
  private final AuthService authService;
  private final JwtService jwtService;

  @Autowired
  public LoginController(AuthService authenticate, JwtService jwtService) {
    this.jwtService = jwtService;
    this.authService = authenticate;
  }

  @PostMapping("/login")
  public ResponseEntity<Result<ResultLogInDTO>> login(@Valid @RequestBody LogInDTO logInDTO) {
    try {
      UserEntity user = authService.authenticate(logInDTO);
      if (user == null) {
        return new ResponseEntity<>(
            Result.failure("Usuario o contraseña incorrectos"), HttpStatus.UNAUTHORIZED);
      }

      String jwtToken = this.jwtService.generateToken(user);

      ResultLogInDTO resultLogInDTO = LogInMapper.toResult(user, jwtToken);

      return new ResponseEntity<>(
          Result.success(resultLogInDTO, "Usuario autenticado con éxito"), HttpStatus.OK);
    } catch (UserArchivedException e) {
      return new ResponseEntity<>(Result.failure("Usuario archivado"), HttpStatus.FORBIDDEN);
    } catch (Exception e) {
      return new ResponseEntity<>(
          Result.failure("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
