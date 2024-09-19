package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ResultProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Integer categoryId;
    private List<String> urlPhotos;

    public static ResultProductDTO create(Integer id, String name, String description, String price, String stock,
            Integer categoryId, List<String> photoUrls) {
        ResultProductDTO resultProductDTO = new ResultProductDTO();
        resultProductDTO.setId(id);
        resultProductDTO.setName(name);
        resultProductDTO.setDescription(description);
        resultProductDTO.setPrice(Double.parseDouble(price));
        resultProductDTO.setStock(Integer.parseInt(stock));
        resultProductDTO.setCategoryId(categoryId);
        resultProductDTO.setUrlPhotos(photoUrls);
        return resultProductDTO;
    }
}
