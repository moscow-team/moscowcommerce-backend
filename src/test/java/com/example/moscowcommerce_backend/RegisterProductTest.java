package com.example.moscowcommerce_backend;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.moscowcommerce_backend.Product.Infrastructure.Controllers.ProductController;
import com.example.moscowcommerce_backend.Product.Application.CreateProductService;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.CreateProductDTO;

import jakarta.validation.ValidationException;

@SpringBootTest
public class RegisterProductTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private CreateProductService productService;
    
    @Test
    public void testPriceRangeValidation() {
        CreateProductDTO validPriceMin = new CreateProductDTO();
        validPriceMin.setPrice(0.01);
        validPriceMin.setName("Producto Test Min");

        CreateProductDTO validPriceMax = new CreateProductDTO();
        validPriceMax.setPrice(9999.99);
        validPriceMax.setName("Producto Test Max");

        CreateProductDTO invalidPriceNegative = new CreateProductDTO();
        invalidPriceNegative.setPrice(-10.0);
        invalidPriceNegative.setDescription("Producto Test Negativo");
        invalidPriceNegative.setName("Producto Test Negativo");

        CreateProductDTO invalidPriceHigh = new CreateProductDTO();
        invalidPriceHigh.setPrice(12000.0);
        invalidPriceHigh.setDescription("Producto Test Exceso");
        invalidPriceHigh.setName("Producto Test Exceso");

        // Espera éxito para valores válidos
        assertDoesNotThrow(() -> productController.createProduct(validPriceMin));
        assertDoesNotThrow(() -> productController.createProduct(validPriceMax));

        // Espera error para precios fuera del rango permitido
        Exception exceptionNegative = assertThrows(ValidationException.class,
                () -> productController.createProduct(invalidPriceNegative));
        assertEquals("El precio minimo es 0.01", exceptionNegative.getMessage());

        Exception exceptionHigh = assertThrows(ValidationException.class,
                () -> productController.createProduct(invalidPriceHigh));
        assertEquals("El precio maximo es 9999.99", exceptionHigh.getMessage());
    }
}
