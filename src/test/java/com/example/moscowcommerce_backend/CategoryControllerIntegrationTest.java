package com.example.moscowcommerce_backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.CreateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.UpdateCategoryDTO;
import com.example.moscowcommerce_backend.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
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

@SpringBootTest(classes = MoscowcommerceBackendApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class CategoryControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ICategoryRepository categoryRepository;

  @Autowired private ObjectMapper objectMapper;

  private static final String MATERA_NAME = "Matera";
  private static final String MATERA_DESCRPTION = "Materas de cuero";
  private Integer createdCategoryId;

  @BeforeEach
  void setup() {
    Category matera = new Category();
    matera.setName(MATERA_NAME);
    matera.setDescription(MATERA_DESCRPTION);
    matera.setCreationDate(LocalDate.now());

    // Guardar la categoría en la base de datos
    Category savedCategory = categoryRepository.save(matera);

    createdCategoryId = savedCategory.getId();
    // imprimir el id de la categoría creada
    System.out.println("Categoría creada con ID: " + createdCategoryId);
  }

  // 1. Test para crear una categoría
  @Test
  void createCategorySuccessfully() throws Exception {
    CreateCategoryDTO categoryDTO = new CreateCategoryDTO();
    categoryDTO.setName("Termos");
    categoryDTO.setDescription("Termos de alta calidad");

    mockMvc
        .perform(
            post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.name").value(categoryDTO.getName()))
        .andExpect(jsonPath("$.data.description").value(categoryDTO.getDescription()));
  }

  @Test
  void createCategoryWithInvalidData() throws Exception {
    CreateCategoryDTO categoryDTO = new CreateCategoryDTO();
    categoryDTO.setName(""); // Nombre inválido
    categoryDTO.setDescription("Mates de cuero");

    mockMvc
        .perform(
            post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  void createCategoryWithExistingName() throws Exception {
    // Crear una categoría con el mismo nombre que ya existe en la base de datos
    CreateCategoryDTO categoryDTO = new CreateCategoryDTO();
    categoryDTO.setName(MATERA_NAME); // El nombre "Matera" ya existe en la base de datos
    categoryDTO.setDescription(MATERA_DESCRPTION);

    mockMvc
        .perform(
            post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
        .andExpect(status().isBadRequest()) // Esperamos un error 400 por conflicto
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(
            jsonPath("$.message").value("Ya existe una categoría con el nombre " + MATERA_NAME));
  }

  // 2. Test para obtener categorías
  @Test
  void getAllCategoriesSuccessfully() throws Exception {
    mockMvc
        .perform(get("/category").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  // 3. Test para eliminar una categoría
  @Test
  void deleteCategorySuccessfully() throws Exception {
    mockMvc
        .perform(delete("/category/" + createdCategoryId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Categoria eliminada con éxito"));
  }

  @Test
  void deleteNonExistentCategory() throws Exception {
    // Intentar eliminar una categoría que no existe
    mockMvc
        .perform(delete("/category/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false));
  }

  // 4. Test para desarchivar una categoría
  @Test
  void unarchivedCategorySuccessfully() throws Exception {
    // Eliminar la categoria con el id createdCategoryId
    mockMvc.perform(
        delete("/category/" + createdCategoryId).contentType(MediaType.APPLICATION_JSON));

    // Desarchivar la categoría eliminada
    mockMvc
        .perform(patch("/category/" + createdCategoryId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Categoria desarchivada con éxito"));
  }

  @Test
  void unarchivedNonExistentCategory() throws Exception {
    // Intentar desarchivar una categoría que no existe
    mockMvc
        .perform(patch("/category/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false));
  }

  // 5. Test para actualizar una categoría
  @Test
  void updateCategorySuccessfully() throws Exception {
    UpdateCategoryDTO updateDTO = new UpdateCategoryDTO();
    updateDTO.setName("Updated Name");
    updateDTO.setDescription("Updated Description");

    mockMvc
        .perform(
            put("/category/" + createdCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.name").value("Updated Name"))
        .andExpect(jsonPath("$.data.description").value("Updated Description"));
  }

  @Test
  void updateNonExistentCategory() throws Exception {
    UpdateCategoryDTO updateDTO = new UpdateCategoryDTO();
    updateDTO.setName("Updated Name");
    updateDTO.setDescription("Updated Description");

    mockMvc
        .perform(
            put("/category/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false));
  }

  // 6. Test para filtrar categorías
  @Test
  void getCategoriesByCriteriaSuccessfully() throws Exception {
    mockMvc
        .perform(get("/category/filters?name=Test").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  void getCategoriesByInvalidCriteria() throws Exception {
    mockMvc
        .perform(
            get("/category/filters?invalidParam=value").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false));
  }
}
