package com.example.moscowcommerce_backend.Shared.Domain.Ports;

import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import java.util.List;

public interface ICriteriaRepository<T> {
  public List<T> findByCriteria(Criteria criteria);
}
