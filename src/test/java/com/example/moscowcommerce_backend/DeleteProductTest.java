package com.example.moscowcommerce_backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Product.Application.DeleteProductService;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductAlreadyArchived;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteProductTest {

  @Mock private IProductRepository repository;

  @InjectMocks private DeleteProductService deleteProductService;

  @BeforeEach
  void setUp() {
    // Configuración adicional si es necesaria
  }

  // Caso de prueba: Eliminar un producto ya archivado
  @Test
  void testDeleteArchivedProduct() {
    Integer productId = 1;
    ProductEntity archivedProduct = new ProductEntity();
    archivedProduct.setId(productId);
    archivedProduct.setArchivedDate(LocalDate.now()); // Producto ya archivado
    archivedProduct.setName("Producto archivado");
    archivedProduct.setDescription("Descripción del producto archivado");
    archivedProduct.setPrice(100.0);
    archivedProduct.setStock(10);
    archivedProduct.setCategory(new CategoryEntity());
    archivedProduct.setPhotos(null);

    // Simulamos que el repositorio encuentra un producto ya archivado
    when(repository.findById(productId)).thenReturn(Optional.of(archivedProduct));

    // Ejecutamos y verificamos que lanza la excepción
    ProductAlreadyArchived exception =
        assertThrows(ProductAlreadyArchived.class, () -> deleteProductService.execute(productId));

    assertEquals("El producto ya está archivado.", exception.getMessage());

    // Verificamos que nunca se llama a save
    verify(repository, never()).save(any(ProductEntity.class));
  }

  // Caso de prueba: Eliminar un producto activo
  @Test
  void testDeleteActiveProduct() {
    Integer productId = 1;
    ProductEntity activeProduct = new ProductEntity();
    activeProduct.setId(productId);
    activeProduct.setArchivedDate(null); // Producto activo

    // Configuramos el comportamiento del repositorio
    when(repository.findById(productId)).thenReturn(Optional.of(activeProduct));
    when(repository.save(any(ProductEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // Ejecutamos el servicio
    ProductEntity archivedProduct = deleteProductService.execute(productId);

    // Verificamos que el producto ha sido archivado correctamente
    assertEquals(activeProduct.getId(), archivedProduct.getId());
    assertNotNull(archivedProduct.getArchivedDate());
    assertEquals(LocalDate.now(), archivedProduct.getArchivedDate());

    // Verificamos que el método save fue llamado una vez
    verify(repository, times(1)).save(any(ProductEntity.class));
  }
}
