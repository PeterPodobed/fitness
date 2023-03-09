package by.it_academy.fitness.dao.api;

import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.dao.entity.products.RecipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface IRecipeDao extends ListCrudRepository<RecipeEntity, UUID> {
    boolean existsByTitle(String title);

    Page<RecipeEntity> findAll(Pageable pageable);




}
