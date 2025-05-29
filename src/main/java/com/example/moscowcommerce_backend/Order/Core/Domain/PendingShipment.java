package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.ShipmentStatus;

public class PendingShipment extends ShipmentState {
  public PendingShipment(ShipmentStatus name) {
    super(name);
  }
}
