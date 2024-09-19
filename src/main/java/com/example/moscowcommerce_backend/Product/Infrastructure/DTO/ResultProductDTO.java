package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import java.util.List;

import com.example.moscowcommerce_backend.Category.Domain.Category;

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

    public static ResultProductDTO create(Integer id, String name, String description, String price, String stock,
            Category category, List<String> photoUrls) {
        ResultProductDTO resultProductDTO = new ResultProductDTO();
        resultProductDTO.setId(id);
        resultProductDTO.setName(name);
        resultProductDTO.setDescription(description);
        resultProductDTO.setPrice(Double.parseDouble(price));
        resultProductDTO.setStock(Integer.parseInt(stock));
        resultProductDTO.setCategory(category);
        resultProductDTO.setUrlPhotos(photoUrls);
        return resultProductDTO;
    }
}
