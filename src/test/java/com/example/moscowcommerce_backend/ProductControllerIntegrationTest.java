package com.example.moscowcommerce_backend;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.CreateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.UpdateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MoscowcommerceBackendApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PRODUCT_NAME = "Termo";
    private static final String PRODUCT_DESCRIPTION = "Termo de acero inoxidable";
    private Integer createdProductId;
    private Integer createdCategoryId;

    @BeforeEach
    void setup() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Termos");
        category.setDescription("Termos de acero inoxidable");

        // mapear el CategoryEntity a un objeto de dominio Category
        Category categoryDomain = CategoryMapper.toDomainFromEntity(category);

        // Guardar la categoría en la base de datos
        Category savedCategory = categoryRepository.save(categoryDomain);

        createdCategoryId = savedCategory.getId();

        // Mapear el objeto de dominio Category de vuelta a CategoryEntity
        CategoryEntity savedCategoryEntity = CategoryMapper.toEntity(savedCategory);

        ProductEntity product = new ProductEntity();
        product.setName(PRODUCT_NAME);
        product.setDescription(PRODUCT_DESCRIPTION);
        product.setPrice(1000.00);
        product.setStock(10);
        product.setPhotos(Collections.emptyList());
        product.setCategory(savedCategoryEntity);

        // Guardar la categoría en la base de datos
        ProductEntity productSaved = productRepository.save(product);

        createdProductId = productSaved.getId();
        // imprimir el id de la categoría creada
        System.out.println("Producto creado con ID: " + createdProductId);
    }

    // 1. Test para obtener todos los productos
    @Test
    void getAllProductsSuccessfully() throws Exception {
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    // 2. Test para crear un producto
    @Test
    void createProductSuccessfully() throws Exception {
        CreateProductDTO productDTO = new CreateProductDTO();
        productDTO.setName("Termo prueba");
        productDTO.setDescription("Termo de acero inoxidable de prueba");
        productDTO.setPrice(2000.00);
        productDTO.setStock(4);
        productDTO.setCategoryId(String.valueOf(createdCategoryId));
        productDTO.setPhotos(Collections.emptyList()); // Imagen vacía

        mockMvc.perform(multipart("/products")
                        .param("name", productDTO.getName())
                        .param("description", productDTO.getDescription())
                        .param("price", String.valueOf(productDTO.getPrice()))
                        .param("stock", String.valueOf(productDTO.getStock()))
                        .param("categoryId", productDTO.getCategoryId())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.data.description").value(productDTO.getDescription()))
                .andExpect(jsonPath("$.data.price").value(productDTO.getPrice()))
                .andExpect(jsonPath("$.data.stock").value(productDTO.getStock()));
    }

    @Test
    void createProductWithInvalidData() throws Exception {
        CreateProductDTO productDTO = new CreateProductDTO();
        productDTO.setName(""); // Nombre vacío (inválido)
        productDTO.setDescription("Termos de baja calidad");
        productDTO.setPrice(300.00);
        productDTO.setStock(2);
        productDTO.setCategoryId(String.valueOf(createdCategoryId));
        productDTO.setPhotos(Collections.emptyList()); // Imagen vacía


        mockMvc.perform(multipart("/products")
                        .param("name", productDTO.getName())
                        .param("description", productDTO.getDescription())
                        .param("price", String.valueOf(productDTO.getPrice()))
                        .param("stock", String.valueOf(productDTO.getStock()))
                        .param("categoryId", productDTO.getCategoryId())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    // 3. Test para eliminar un producto
    @Test
    void deleteProductSuccessfully() throws Exception {
        mockMvc.perform(delete("/products/" + createdProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto eliminado con exito."));
    }

    @Test
    void deleteNonExistentProduct() throws Exception {
        mockMvc.perform(delete("/products/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    // 4. Test para desarchivar un producto
    @Test
    void unarchiveProductSuccessfully() throws Exception {
        // Primero eliminar el producto
        mockMvc.perform(delete("/products/" + createdProductId)
                .contentType(MediaType.APPLICATION_JSON));

        // Desarchivar el producto
        mockMvc.perform(patch("/products/" + createdProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto desarchivado con exito."));
    }

    @Test
    void unarchiveNonExistentProduct() throws Exception {
        mockMvc.perform(patch("/products/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    // 5. Test para actualizar un producto
    @Test
    void updateProductSuccessfully() throws Exception {
        UpdateProductDTO updateProductDTO = new UpdateProductDTO();
        updateProductDTO.setName("Termo Actualizado");
        updateProductDTO.setDescription("Termo de acero inoxidable actualizado");
        updateProductDTO.setPrice(333.00);
        updateProductDTO.setStock(1);
        updateProductDTO.setCategoryId(String.valueOf(createdCategoryId));
        updateProductDTO.setPhotos(Collections.emptyList()); // Imagen vacía

        mockMvc.perform(MockMvcRequestBuilders.put("/products/" + createdProductId) // Usar PUT para la actualización
                        .param("name", updateProductDTO.getName())
                        .param("description", updateProductDTO.getDescription())
                        .param("price", String.valueOf(updateProductDTO.getPrice()))
                        .param("stock", String.valueOf(updateProductDTO.getStock()))
                        .param("categoryId", updateProductDTO.getCategoryId())
                        .contentType(MediaType.MULTIPART_FORM_DATA)  // Definir que el contenido es de tipo multipart
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(updateProductDTO.getName()))
                .andExpect(jsonPath("$.data.description").value(updateProductDTO.getDescription()))
                .andExpect(jsonPath("$.data.price").value(updateProductDTO.getPrice()))
                .andExpect(jsonPath("$.data.stock").value(updateProductDTO.getStock()));
    }

    @Test
    void updateNonExistentProduct() throws Exception {
        UpdateProductDTO updateProductDTO = new UpdateProductDTO();
        updateProductDTO.setName("Producto No Existente");
        updateProductDTO.setDescription("Descripción del producto no existente");
        updateProductDTO.setPrice(333.00);
        updateProductDTO.setStock(1);
        updateProductDTO.setCategoryId(String.valueOf(createdCategoryId));
        updateProductDTO.setPhotos(Collections.emptyList()); // Imagen vacía

        mockMvc.perform(MockMvcRequestBuilders.put("/products/999" ) // Usar PUT para la actualización
                        .param("name", updateProductDTO.getName())
                        .param("description", updateProductDTO.getDescription())
                        .param("price", String.valueOf(updateProductDTO.getPrice()))
                        .param("stock", String.valueOf(updateProductDTO.getStock()))
                        .param("categoryId", updateProductDTO.getCategoryId())
                        .contentType(MediaType.MULTIPART_FORM_DATA)  // Definir que el contenido es de tipo multipart
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("[Producto: "+ updateProductDTO.getName() + "] El producto no existe"));
    }
}
