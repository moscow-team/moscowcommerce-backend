package com.example.moscowcommerce_backend.Order.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Order.Core.Domain.DeliveredShipment;
import com.example.moscowcommerce_backend.Order.Core.Domain.InProgressShipment;
import com.example.moscowcommerce_backend.Order.Core.Domain.PendingShipment;
import com.example.moscowcommerce_backend.Order.Core.Domain.Shipment;
import com.example.moscowcommerce_backend.Order.Core.Domain.ShipmentState;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.ShipmentEntity;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.ShipmentStatus;

public abstract class ShipmentMapper {
   public static ShipmentEntity mapToEntity(Shipment shipment) {
      ShipmentEntity shipmentEntity = new ShipmentEntity();
      shipmentEntity.setAddress(shipment.getAddress());
      shipmentEntity.setStatus(shipment.getState().getName());
      return shipmentEntity;
   } 

    public static Shipment mapToDomain(ShipmentEntity entity) {
        Shipment shipment = new Shipment(entity.getAddress());
        shipment.changeState(getState(entity.getStatus()));
        return shipment;
    }

    private static ShipmentState getState(ShipmentStatus status) {
        if (status == ShipmentStatus.PENDING) {
            return new PendingShipment(status);
        } else if (status == ShipmentStatus.IN_PROGRESS) {
            return new InProgressShipment(status);
        } else {
            return new DeliveredShipment(status);
        }
    }
}
