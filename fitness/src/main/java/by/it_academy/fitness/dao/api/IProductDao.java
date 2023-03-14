package by.it_academy.fitness.dao.api;

import by.it_academy.fitness.dao.entity.products.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface IProductDao extends ListCrudRepository<ProductEntity, UUID> {
    Page<ProductEntity> findAll(Pageable pageable);
    Optional<ProductEntity> findByTitle (String title);

    ProductEntity findByUuid(UUID uuid);

}
