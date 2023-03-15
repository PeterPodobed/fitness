package by.it_academy.fitness.service.convertion.products;

import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.service.convertion.products.api.IProductEntityToDto;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToDto implements IProductEntityToDto {
    @Override
    public ProductDto convertProductEntityToDto(ProductEntity productEntity)
            throws MultipleErrorResponse {
        return new ProductDto(productEntity.getUuid(),
                productEntity.getDt_create(),
                productEntity.getDt_update(),
                productEntity.getTitle(),
                productEntity.getWeight(),
                productEntity.getCalories(),
                productEntity.getProteins(),
                productEntity.getFats(),
                productEntity.getCarbohydrates()
        );
    }
}
