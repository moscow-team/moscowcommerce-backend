package com.example.moscowcommerce_backend.Order.Core.Domain;

import java.time.LocalDate;
import java.util.List;

import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Users.Domain.User;

public class Order {
    private OrderId id;
    private User user;
    private List<Product> products;
    private Payment payment;
    private Shipment shipment;
    private LocalDate createTime;
    private LocalDate updateTime;
    private LocalDate archivedTime;

    public Order(Integer id, User user, List<Product> products, Payment payment, Shipment shipment, LocalDate createTime, LocalDate updateTime, LocalDate archivedTime) {
        this.id = new OrderId(id);
        this.user = user;
        this.products = products;
        this.payment = payment;
        this.shipment = shipment;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.archivedTime = archivedTime;
    }
    
    public Order(Integer id, User user, List<Product> products) {
        this.id = new OrderId(id);
        this.user = user;
        this.products = products;
        this.payment = new Payment();
        this.shipment = new Shipment();
    }

    public Order(User user, List<Product> products, Payment payment, Shipment shipment) {
        this.user = user;
        this.products = products;
        this.payment = payment;
        this.shipment = shipment;
    }

    public OrderId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Payment getPayment() {
        return payment;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public LocalDate getArchivedTime() {
        return archivedTime;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public void setArchivedTime(LocalDate archivedTime) {
        this.archivedTime = archivedTime;
    }

    public void archive() {
        this.archivedTime = LocalDate.now();
    }

    public Boolean isArchived() {
        return this.archivedTime != null;
    }
}
