package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.ShipmentStatus;

public class InProgressShipment extends ShipmentState {
   public InProgressShipment(ShipmentStatus name) {
      super(name);
   } 
}
