package com.example.moscowcommerce_backend.Users.Application;

import com.example.moscowcommerce_backend.Users.Domain.*;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.*;
import com.example.moscowcommerce_backend.Users.Application.Interfaces.*;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

import java.util.Optional;

@Service
public final class CreateUserService implements ICreateUserService{
    private final IUserRepository repository;

    @Autowired
    public CreateUserService(IUserRepository repository) {
        this.repository = repository;
    }

    public void create(User user) {
        // Esta validacion se podria obviar ya que la base de datos no permite generar dos usuarios con el mismo email,
        // de esta forma manejamos nosotros la excepcion
        // Primero verificamos que no exista un usuario con este email
        Optional<UserEntity> userExist = this.repository.findByEmail(user.getEmail());
        


        // Si existe, devolvemos la excepción
        if (userExist.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        // Mapeamos la entidad de dominio a la entidad de base de datos
        UserEntity userEntity = UserMapper.toEntity(user);

        System.out.println(userEntity);
        // Guardamos usuario en la BD
        this.repository.save(userEntity);
    }
}