package com.example.moscowcommerce_backend.Shared.Infrastructure;

public class Result<T> {
  private final T data;
  private final String message;
  private final Status status;

  public enum Status {
    SUCCESS,
    FAILURE
  }

  private Result(T data, String message, Status status) {
    this.data = data;
    this.message = message;
    this.status = status;
  }

  public static <T> Result<T> success(T data, String message) {
    return new Result<>(data, message, Status.SUCCESS);
  }

  public static <T> Result<T> failure(String message) {
    return new Result<>(null, message, Status.FAILURE);
  }

  public static <T> Result<T> failure(String message, T data) {
    return new Result<>(data, message, Status.FAILURE);
  }

  public boolean isSuccess() {
    return status == Status.SUCCESS;
  }

  public boolean isFailure() {
    return status == Status.FAILURE;
  }

  public T getData() {
    return data;
  }

  public String getMessage() {
    return message;
  }

  public Status getStatus() {
    return status;
  }
}
