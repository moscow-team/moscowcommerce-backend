package com.example.moscowcommerce_backend.Category.Domain;

public class Category {
    private Integer id;
    private String name;
    private String description;


    public Category(){
        this.id = null;
        this.name = null;
        this.description = null;
    }

    public Category(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Category(Integer id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
