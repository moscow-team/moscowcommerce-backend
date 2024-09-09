package com.example.moscowcommerce_backend.Category.Infrastructure.DTO;

import lombok.Data;

@Data
public class ResultCategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private String creationDate;
    private String modificationDate;
    private String archivedDate;

    public static ResultCategoryDTO create(Integer id, String name, String description, String creationDate, String modificationDate, String archivedDate) {
        ResultCategoryDTO resultCategoryDTO = new ResultCategoryDTO();
        resultCategoryDTO.setId(id);
        resultCategoryDTO.setName(name);
        resultCategoryDTO.setDescription(description);
        resultCategoryDTO.setCreationDate(creationDate);
        resultCategoryDTO.setModificationDate(modificationDate);
        resultCategoryDTO.setArchivedDate(archivedDate);
        return resultCategoryDTO;
    }
}
