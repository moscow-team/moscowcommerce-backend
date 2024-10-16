package com.example.moscowcommerce_backend.Users.Application.Interfaces;

import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResetPasswordDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;

public interface IResetPassword {
    ResultUserDTO resetPassword(ResetPasswordDTO resetPasswordDTO);
}
