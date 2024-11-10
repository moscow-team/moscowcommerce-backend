package com.example.moscowcommerce_backend;

import com.example.moscowcommerce_backend.Product.Application.DeleteProductService;
import com.example.moscowcommerce_backend.Product.Infrastructure.Controllers.ProductController;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.ResultProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class DeleteProductTest {

    @Mock
    private DeleteProductService deleteProductService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        // Setup inicial
    }

    // Caso de prueba: Eliminar un producto ya archivado
    @Test
    void testDeleteArchivedProduct() {
        // Arrange: Configurar los datos de entrada y comportamiento simulado
        Integer productId = 1;
        
        // Simulamos que el producto ya está archivado
        when(deleteProductService.execute(productId)).thenThrow(new RuntimeException("Producto ya archivado"));

        // Act: Llamamos al método deleteProduct del controlador
        ResponseEntity<Result<ResultProductDTO>> response = productController.deleteProduct(productId);

        // Assert: Verificamos el resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isFailure());
        assertEquals("Producto ya archivado", response.getBody().getMessage());

        // Verificamos que el servicio fue llamado una vez
        verify(deleteProductService, times(1)).execute(productId);
    }

    // Caso de prueba: Eliminar un producto activo
    @Test
    void testDeleteActiveProduct() {
        // Arrange: Configurar los datos de entrada y comportamiento simulado
        Integer productId = 2;
        ProductEntity activeProduct = new ProductEntity(); // Configurar las propiedades necesarias para el test
        LocalDate timeNow = LocalDate.now();
        activeProduct.setArchivedDate(timeNow);

        // Simulamos que el producto es eliminado correctamente
        when(deleteProductService.execute(productId)).thenReturn(activeProduct);

        // Act: Llamamos al método deleteProduct del controlador
        ResponseEntity<Result<ResultProductDTO>> response = productController.deleteProduct(productId);

        // Assert: Verificamos el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Producto eliminado con exito.", response.getBody().getMessage());

        // Verificamos que el servicio fue llamado una vez
        verify(deleteProductService, times(1)).execute(productId);
    }
}
