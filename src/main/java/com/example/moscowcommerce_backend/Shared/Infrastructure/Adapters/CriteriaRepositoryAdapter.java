package com.example.moscowcommerce_backend.Shared.Infrastructure.Adapters;

import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Filter;
import com.example.moscowcommerce_backend.Shared.Domain.Ports.ICriteriaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CriteriaRepositoryAdapter<T, R> implements ICriteriaRepository<R> {
    @PersistenceContext
    private EntityManager entityManager;

    public CriteriaRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<R> findByCriteria(Criteria criteria, Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        // Aplicar los filtros dinámicamente
        Predicate[] predicates = criteria.getFilters().stream()
                .map(filter -> buildPredicate(filter, criteriaBuilder, root))
                .toArray(Predicate[]::new);

        query.where(predicates);

        List<T> resultList = entityManager.createQuery(query).getResultList();
        return resultList.stream().map(this::toDomain).collect(Collectors.toList());
    }

    // Método auxiliar para construir predicados a partir de un filtro
    private Predicate buildPredicate(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> root) {
        String field = filter.getField();
        String value = filter.getValue();
        String operator = filter.getOperator().getValue();

        // Detectar si el campo pertenece a una relación
        if (field.contains(".")) {
            // El campo tiene un formato como "category.id", así que hacemos un join.
            String[] fieldParts = field.split("\\.");
            String relation = fieldParts[0]; // e.g., "category"
            String relationField = fieldParts[1]; // e.g., "id"

            // Realizar un join a la relación
            Join<Object, Object> join = root.join(relation);
            Path<String> path = join.get(relationField);

            return createPredicate(criteriaBuilder, path, value, operator);
        } else {
            // Si no es un campo relacionado, proceder como antes
            Path<String> path = root.get(field);
            return createPredicate(criteriaBuilder, path, value, operator);
        }
    }

    // Método auxiliar para crear el Predicate según el operador
    private Predicate createPredicate(CriteriaBuilder criteriaBuilder, Path<String> path, String value,
            String operator) {
        // Si el valor es "null" o "NULL", entonces se trata de una comparación con nulo
        // printeamo el valor de path, operator y value

        if (value.equalsIgnoreCase("null")) {
            if (operator.equalsIgnoreCase("=")) {
                return criteriaBuilder.isNull(path);
            } else {
                return criteriaBuilder.isNotNull(path);
            }
        }

        switch (operator) {
            case "=":
                return criteriaBuilder.equal(path, value);
            case "LIKE":
                return criteriaBuilder.like(path, "%" + value + "%");
            case "<":
                return criteriaBuilder.lessThan(path, value);
            case "<=":
                return criteriaBuilder.lessThanOrEqualTo(path, value);
            case ">":
                return criteriaBuilder.greaterThan(path, value);
            case ">=":
                return criteriaBuilder.greaterThanOrEqualTo(path, value);
            case "!=":
                return criteriaBuilder.notEqual(path, value);
            case "IN":
                return path.in(value);
            default:
                throw new IllegalArgumentException("Operador no soportado: " + operator);
        }
    }

    protected abstract R toDomain(T entity);
}
