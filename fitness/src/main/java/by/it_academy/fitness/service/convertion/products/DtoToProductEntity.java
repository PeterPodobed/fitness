package by.it_academy.fitness.service.convertion.products;

import by.it_academy.fitness.core.dto.products.ProductCreateDto;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.service.convertion.products.api.IDtoToProductEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DtoToProductEntity implements IDtoToProductEntity {
    @Override
    public ProductEntity convertDtoToProductEntity(ProductCreateDto productCreateDto) {
        return new ProductEntity(UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                productCreateDto.getTitle(),
                productCreateDto.getWeight(),
                productCreateDto.getCalories(),
                productCreateDto.getProteins(),
                productCreateDto.getFats(),
                productCreateDto.getCarbohydrates());
    }
}
