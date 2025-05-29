package com.example.moscowcommerce_backend.Shared.Domain.Criteria;

public class Filter {
  private String field;
  private String value;
  private OperatorValueObject operator;

  private Filter(String field, String value, String operator) {
    this.field = field;
    this.value = value;
    this.operator = OperatorValueObject.create(operator);
  }

  public static Filter create(String field, String value, String operator) {
    return new Filter(field, value, operator);
  }

  public static Filter create(String field, String value) {
    return new Filter(field, value, "=");
  }

  public String getField() {
    return field;
  }

  public String getValue() {
    return value;
  }

  public OperatorValueObject getOperator() {
    return operator;
  }
}
