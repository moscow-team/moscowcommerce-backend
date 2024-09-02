package com.example.moscowcommerce_backend.Users.Application.Interfaces;

import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;

import java.util.List;

public interface IListUserService {
    List<ResultUserDTO> findAll();
}
