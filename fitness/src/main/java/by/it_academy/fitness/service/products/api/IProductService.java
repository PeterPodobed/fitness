package by.it_academy.fitness.service.products.api;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.ProductCreateDto;
import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.products.ProductEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {

    boolean createProduct(ProductCreateDto productCreateDTO);

    void updateProduct(UUID uuid, LocalDateTime dt_update, ProductCreateDto productCreateDto);

    Optional<ProductEntity> findByUUID (UUID uuid);

    PageDto<ProductDto> getPage (int number, int size) throws MultipleErrorResponse;
}
