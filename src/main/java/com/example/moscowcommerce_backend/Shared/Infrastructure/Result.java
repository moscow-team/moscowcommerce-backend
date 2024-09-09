package com.example.moscowcommerce_backend.Shared.Infrastructure;

public class Result<T> {
    private final T data;          // Dato de éxito o fallo
    private final String message;  // Mensaje descriptivo
    private final Status status;   // Estado: SUCCESS o FAILURE

    // Enum para representar los estados
    public enum Status {
        SUCCESS,
        FAILURE
    }

    // Constructor privado
    private Result(T data, String message, Status status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    // Método estático para crear un resultado exitoso
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(data, message, Status.SUCCESS);
    }

    // Método estático para crear un resultado fallido
    public static <T> Result<T> failure(String message) {
        return new Result<>(null, message, Status.FAILURE);
    }

    // Método para verificar si el resultado es exitoso
    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    // Método para verificar si el resultado es un fallo
    public boolean isFailure() {
        return status == Status.FAILURE;
    }

    // Obtener el dato en caso de éxito o null en caso de fallo
    public T getData() {
        return data;
    }

    // Obtener el mensaje descriptivo
    public String getMessage() {
        return message;
    }

    // Obtener el estado
    public Status getStatus() {
        return status;
    }
}
