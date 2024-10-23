package com.example.moscowcommerce_backend.Shared.Domain.Ports;

import java.util.List;

import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;

public interface ICriteriaRepository<T> { 
    public List<T> findByCriteria(Criteria criteria);
}
