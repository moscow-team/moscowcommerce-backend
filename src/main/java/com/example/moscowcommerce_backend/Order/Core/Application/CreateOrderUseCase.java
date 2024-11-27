package com.example.moscowcommerce_backend.Order.Core.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Order.Core.Application.Ports.ICreateOrderUseCase;
import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Core.Domain.Payment;
import com.example.moscowcommerce_backend.Order.Core.Domain.Shipment;
import com.example.moscowcommerce_backend.Order.Core.Domain.Exceptions.InsufficientStockExeption;
import com.example.moscowcommerce_backend.Order.Core.Domain.Ports.IOrderRepository;
import com.example.moscowcommerce_backend.Order.Infrastructure.DTOs.CreateOrderDTO;
import com.example.moscowcommerce_backend.Order.Infrastructure.DTOs.ProductOrderDTO;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductNotFoundException;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;
import com.example.moscowcommerce_backend.Shared.Domain.PriceValueObject;
import com.example.moscowcommerce_backend.Users.Application.Interfaces.IListUserService;
import com.example.moscowcommerce_backend.Users.Domain.User;

import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public final class CreateOrderUseCase implements ICreateOrderUseCase {
    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final IListUserService listUserService;

    @Autowired
    public CreateOrderUseCase(
            IOrderRepository orderRepository,
            IProductRepository productRepository,
            IListUserService listUserService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.listUserService = listUserService;
    }

    @Override
    public Order create(CreateOrderDTO order, String userEmail) {
        List<Product> products = validateAndRetrieveProducts(order.getProducts());
        User user = listUserService.findByEmail(userEmail);

        PriceValueObject amountPayment = calculateTotalAmount(order, products);
        Payment payment = new Payment(order.getPayment().getPaymentMethod(), amountPayment);
        Shipment shipment = new Shipment(order.getShipment().getAddress());

        Order newOrder = new Order(user, products, payment, shipment);

        return saveOrderAndUpdateProducts(newOrder, products);
    }

    private List<Product> validateAndRetrieveProducts(List<ProductOrderDTO> productOrders) {
        return productOrders.stream()
                .map(this::validateAndReduceStock)
                .collect(Collectors.toList());
    }

    private Order saveOrderAndUpdateProducts(Order order, List<Product> products) {
        List<ProductEntity> productEntities = products.stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList());

        productEntities.forEach(productRepository::save);

        return orderRepository.save(order);
    }

    private Product validateAndReduceStock(ProductOrderDTO productOrder) {
        Product product = productRepository.findById(productOrder.getProductId())
                .map(ProductMapper::toDomainFromEntity)
                .orElseThrow(() -> new ProductNotFoundException(
                        "El producto con id " + productOrder.getProductId() + " no existe"));

        if (product.getStock() < productOrder.getQuantity()) {
            throw new InsufficientStockExeption(
                    "El producto con id " + product.getId() + " no tiene suficiente stock. Stock disponible: "
                            + product.getStock());
        }

        product.setStock(product.getStock() - productOrder.getQuantity());

        return product;
    }

    private PriceValueObject calculateTotalAmount(CreateOrderDTO order, List<Product> products) {
        PriceValueObject total = new PriceValueObject(BigDecimal.ZERO);
        for (Product product : products) {
            Integer quantity = getQuantityProduct(order.getProducts(), product.getId());
            double productPrice = product.getPrice().doubleValue();
            total = total.add(new PriceValueObject(BigDecimal.valueOf(productPrice * quantity)));
        }
        return total;
    }

    private Integer getQuantityProduct(List<ProductOrderDTO> products, Integer productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(ProductOrderDTO::getQuantity)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cantidad no encontrada para el producto con id " + productId));
    }
}

// public final class CreateOrderUseCase implements ICreateOrderUseCase {
// private final IOrderRepository orderRepository;
// private final IProductRepository productRepository;
// private final IListUserService listUserService;

// @Autowired
// public CreateOrderUseCase(
// IOrderRepository orderRepository,
// IProductRepository productRepository,
// IListUserService listUserService
// ) {
// this.orderRepository = orderRepository;
// this.productRepository = productRepository;
// this.listUserService = listUserService;
// }

// @Override
// public Order create(CreateOrderDTO order, String userEmail) {
// // Primero se valida que los productos existan
// List<Product> products = new ArrayList<Product>();
// for (ProductOrderDTO product : order.getProducts()) {
// Optional<ProductEntity> productEntity =
// productRepository.findById(product.getProductId());
// if (productEntity.isEmpty()) {
// throw new ProductNotFoundException("El producto con id " +
// product.getProductId() + " no existe");
// }

// products.add(ProductMapper.toDomainFromEntity(productEntity.get()));
// }

// // Obtenemos el usuario que realiza la orden
// User user = listUserService.findByEmail(userEmail);

// // Creamos el pago y el shipment con los datos del DTO
// AmountPayment amountPayment = createAmountPayment(order, products);
// Payment payment = new Payment(order.getPayment().getPaymentMethod(),
// amountPayment);

// Shipment shipment = new Shipment(order.getShipment().getAddress());

// // Creamos la orden con los datos del DTO

// Order newOrder = new Order(user, products, payment, shipment);

// // Guardamos la orden en la base de datos
// return orderRepository.save(newOrder);
// }

// private AmountPayment createAmountPayment(CreateOrderDTO order, List<Product>
// products) {
// AmountPayment total = new AmountPayment(BigDecimal.ZERO);
// for (Product product : products) {
// Integer quantity = getQuantityProduct(order.getProducts(), product.getId());
// double productPrice = product.getPrice().doubleValue();
// total = (AmountPayment) total.add(new
// AmountPayment(BigDecimal.valueOf(productPrice * quantity)));
// }
// return total;

// }

// private Integer getQuantityProduct(List<ProductOrderDTO> products, Integer
// productId) {
// return products.stream()
// .filter(p -> p.getProductId() == productId)
// .findFirst()
// .get()
// .getQuantity();
// }
// }
