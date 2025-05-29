package com.example.moscowcommerce_backend.Shared.Domain.StatePattern;

public abstract class State<T> {
  private StateContext<T> context;

  public State() {}

  public State(StateContext<T> context) {
    this.context = context;
  }

  public void setContext(StateContext<T> context) {
    this.context = context;
  }

  public StateContext<T> getContext() {
    return context;
  }

  public void changeState(T state) {
    this.context.changeState(state);
  }
}
