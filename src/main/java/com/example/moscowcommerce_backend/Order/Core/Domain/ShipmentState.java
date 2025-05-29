package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.ShipmentStatus;
import com.example.moscowcommerce_backend.Shared.Domain.StatePattern.State;

public abstract class ShipmentState extends State<Shipment> {

  private ShipmentStatus name;

  public ShipmentState(ShipmentStatus name) {
    this.validateStatus(name);
  }

  public ShipmentStatus getName() {
    return name;
  }

  private void validateStatus(ShipmentStatus name) {
    if (name != ShipmentStatus.PENDING
        && name != ShipmentStatus.IN_PROGRESS
        && name != ShipmentStatus.DELIVERED) {
      throw new IllegalArgumentException("Invalid status");
    }

    this.name = name;
  }
}
