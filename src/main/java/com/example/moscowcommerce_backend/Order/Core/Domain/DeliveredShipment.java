package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.ShipmentStatus;

public class DeliveredShipment extends ShipmentState {
  public DeliveredShipment(ShipmentStatus name) {
    super(name);
  }
}
