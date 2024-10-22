package com.example.moscowcommerce_backend.Users.Application.Interfaces;

public interface ISendEmail {
    public void sendEmail(String to, String subject, String content);
}
