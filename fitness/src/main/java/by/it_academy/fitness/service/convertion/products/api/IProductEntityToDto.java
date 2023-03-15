package by.it_academy.fitness.service.convertion.products.api;

import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.products.ProductEntity;

public interface IProductEntityToDto {

    ProductDto convertProductEntityToDto(ProductEntity productEntity) throws MultipleErrorResponse;
}
