package by.it_academy.fitness.service.convertion.products.api;

import by.it_academy.fitness.core.dto.products.ProductCreateDto;
import by.it_academy.fitness.dao.entity.products.ProductEntity;

public interface IDtoToProductEntity {

    ProductEntity convertDtoToProductEntity(ProductCreateDto productCreateDto);

}
