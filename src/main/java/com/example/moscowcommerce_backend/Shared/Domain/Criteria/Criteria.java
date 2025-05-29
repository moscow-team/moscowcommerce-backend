package com.example.moscowcommerce_backend.Shared.Domain.Criteria;

import java.util.List;

public class Criteria {
  private List<Filter> filters;

  private Criteria(List<Filter> filters) {
    this.filters = filters;
  }

  public static Criteria create(List<Filter> filters) {
    return new Criteria(filters);
  }

  public List<Filter> getFilters() {
    return filters;
  }

  public void setFilters(List<Filter> filters) {
    this.filters = filters;
  }

  public void addFilter(Filter filter) {
    this.filters.add(filter);
  }
}
