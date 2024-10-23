package com.example.moscowcommerce_backend.Shared.Domain.Criteria;

import java.util.List;

import com.example.moscowcommerce_backend.Shared.Domain.Exceptions.InvalidOperatorException;

public class OperatorValueObject {
    private static final List<String> POSSIBLE_OPERATORS = List.of("=", ">", "<", ">=", "<=", "LIKE");
    private String operator;

    private OperatorValueObject(String operator) {
        this.operator = operator;
    }

    public static OperatorValueObject create(String operator) {
        if (!POSSIBLE_OPERATORS.contains(operator)) {
            throw new InvalidOperatorException("El operador " + operator + " no es válido");
        }
        return new OperatorValueObject(operator);
    }

    public String getValue() {
        return operator;
    }
}
