package com.example.moscowcommerce_backend.Order.Infrastructure.DTOs;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentType;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.ResultProductDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import java.util.List;

public class ResultOrderDTO {
  private Integer id;
  private ResultUserDTO user;
  private String createtime;
  private String updatetime;
  private Double total;
  private List<ResultProductDTO> products;
  private PaymentType paymentMethod;

  public ResultOrderDTO(
      Integer id,
      ResultUserDTO user,
      String createtime,
      String updatetime,
      Double total,
      List<ResultProductDTO> products,
      PaymentType paymentMethod) {
    this.id = id;
    this.user = user;
    this.createtime = createtime;
    this.updatetime = updatetime;
    this.total = total;
    this.products = products;
    this.paymentMethod = paymentMethod;
  }

  public Integer getId() {
    return id;
  }

  public ResultUserDTO getUser() {
    return user;
  }

  public String getCreatetime() {
    return createtime;
  }

  public String getUpdatetime() {
    return updatetime;
  }

  public Double getTotal() {
    return total;
  }

  public List<ResultProductDTO> getProducts() {
    return products;
  }

  public PaymentType getPaymentMethod() {
    return paymentMethod;
  }
}
