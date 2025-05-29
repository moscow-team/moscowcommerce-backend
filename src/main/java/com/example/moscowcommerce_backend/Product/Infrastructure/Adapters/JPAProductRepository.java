package com.example.moscowcommerce_backend.Product.Infrastructure.Adapters;

import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;
import com.example.moscowcommerce_backend.Product.Infrastructure.Ports.IProductRepositoryJPA;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Adapters.CriteriaRepositoryAdapter;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JPAProductRepository extends CriteriaRepositoryAdapter<ProductEntity, Product>
    implements IProductRepository {

  private final IProductRepositoryJPA productRepository;

  @Autowired
  public JPAProductRepository(
      IProductRepositoryJPA productRepository, EntityManager entityManager) {
    super(entityManager);
    this.productRepository = productRepository;
  }

  // Método para obtener todos los productos
  public List<ProductEntity> findAll() {
    return productRepository.findAll().stream().collect(Collectors.toList());
  }

  // Método para buscar un producto por ID
  public Optional<ProductEntity> findById(Integer id) {
    return productRepository.findById(id);
  }

  // Método para buscar un producto por nombre (ignorando mayúsculas)
  public Optional<ProductEntity> findByNameIgnoreCase(String name) {
    return productRepository.findByNameIgnoreCase(name);
  }

  // Método para guardar un producto
  public ProductEntity save(ProductEntity product) {
    return productRepository.save(product);
  }

  // Método para convertir de ProductEntity a Product (entidad de dominio)
  protected Product toDomain(ProductEntity entity) {
    return ProductMapper.toDomainFromEntity(entity);
  }

  @Override
  public List<Product> findByCriteria(Criteria criteria) {
    return super.findByCriteria(criteria, ProductEntity.class);
  }

  // Método para convertir de Product (entidad de dominio) a ProductEntity
  // private ProductEntity toEntity(Product product) {
  // return ProductMapper.toEntity(product);
  // }
}
