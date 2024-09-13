package com.example.moscowcommerce_backend.Product.Domain;

public class ProductPhoto {
    private Integer id;
    private String urlPhoto;

    public ProductPhoto(Integer id, String urlPhoto) {
        this.id = id;
        this.urlPhoto = urlPhoto;
    }

    public ProductPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
