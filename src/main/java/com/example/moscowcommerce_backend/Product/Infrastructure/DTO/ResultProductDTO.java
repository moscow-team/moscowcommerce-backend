package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import java.util.List;
import lombok.Data;

@Data
public class ResultProductDTO {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private Integer stock;
  private Category category;
  private List<String> urlPhotos;
  private boolean archived;
  private String archivedDate;

  public static ResultProductDTO create(
      Integer id,
      String name,
      String description,
      String price,
      String stock,
      Category category,
      List<String> photoUrls,
      boolean archived,
      String archivedDate) {
    ResultProductDTO resultProductDTO = new ResultProductDTO();
    resultProductDTO.setId(id);
    resultProductDTO.setName(name);
    resultProductDTO.setDescription(description);
    resultProductDTO.setPrice(Double.parseDouble(price));
    resultProductDTO.setStock(Integer.parseInt(stock));
    resultProductDTO.setCategory(category);
    resultProductDTO.setUrlPhotos(photoUrls);
    resultProductDTO.setArchived(archived);
    resultProductDTO.setArchivedDate(archivedDate);
    return resultProductDTO;
  }
}
