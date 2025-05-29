package com.example.moscowcommerce_backend.Shared.Domain.StatePattern;

public abstract class StateContext<T> {
  private T state;

  public StateContext(T state) {
    this.state = state;
  }

  public void changeState(T state) {
    this.state = state;
  }

  public T getState() {
    return state;
  }
}
