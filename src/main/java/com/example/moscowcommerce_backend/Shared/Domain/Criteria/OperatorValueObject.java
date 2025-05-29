package com.example.moscowcommerce_backend.Shared.Domain.Criteria;

import com.example.moscowcommerce_backend.Shared.Domain.Exceptions.InvalidOperatorException;
import java.util.List;

public class OperatorValueObject {
  private static final List<String> POSSIBLE_OPERATORS =
      List.of("=", ">", "<", ">=", "<=", "LIKE", "IN", "!=");
  private String operator;

  private OperatorValueObject(String operator) {
    this.operator = operator;
  }

  public static OperatorValueObject create(String operator) {
    if (!POSSIBLE_OPERATORS.contains(operator.toUpperCase())) {
      throw new InvalidOperatorException("El operador " + operator + " no es válido");
    }
    return new OperatorValueObject(operator.toUpperCase());
  }

  public String getValue() {
    return operator;
  }
}
