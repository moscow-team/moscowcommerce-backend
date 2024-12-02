package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.ShipmentStatus;
import com.example.moscowcommerce_backend.Shared.Domain.StatePattern.StateContext;

public class Shipment extends StateContext<ShipmentState> {

    private ShipmentId id;
    private String address;

    public Shipment() {
        super(new PendingShipment(ShipmentStatus.PENDING));
    }

    public Shipment(ShipmentId id, String address) {
        super(new PendingShipment(ShipmentStatus.PENDING));
        this.id = id;
        this.address = address;
    }

    public Shipment(String address) {
        super(new PendingShipment(ShipmentStatus.PENDING));
        this.address = address;
    }

    public ShipmentId getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void inProgress() {
        this.changeState(new InProgressShipment(ShipmentStatus.IN_PROGRESS));
    }

    public void delivered() {
        this.changeState(new DeliveredShipment(ShipmentStatus.DELIVERED));
    }
}
