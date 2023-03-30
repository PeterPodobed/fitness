package by.it_academy.fitness.service.convertion.recipe;

import by.it_academy.fitness.core.dto.products.CompositionDto;
import by.it_academy.fitness.core.dto.products.CompositionDtoParam;
import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.dto.products.RecipeDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.products.CompositionEntity;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.dao.entity.products.RecipeEntity;
import by.it_academy.fitness.service.convertion.products.api.IProductEntityToDto;
import by.it_academy.fitness.service.convertion.recipe.api.IRecipeEntityToDto;
import by.it_academy.fitness.service.products.api.IProductService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RecipeEntityToDto implements IRecipeEntityToDto {

    private final IProductService iProductService;

    public RecipeEntityToDto(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @Override
    public RecipeDto convertRecipeEntityToDto(RecipeEntity recipeEntity) throws MultipleErrorResponse {

        List<CompositionEntity> compositionProductValue = recipeEntity.getComposition();
        List<CompositionDtoParam> compositionProduct = new ArrayList<>();

        for (CompositionEntity compositionEntity : compositionProductValue) {
            ProductDto productEntity = new ProductDto();
            Optional<ProductEntity> searchProduct = iProductService.findByUUID(compositionEntity.getProductEntity().getUuid());
            ProductEntity product = searchProduct.get();

            double ratio = (double) compositionEntity.getWeight() / product.getWeight();
            productEntity.setUuid(product.getUuid());
            productEntity.setDt_create(product.getDt_create());
            productEntity.setDt_update(product.getDt_update());
            productEntity.setTitle(product.getTitle());
            productEntity.setWeight(product.getWeight());
            productEntity.setCalories(product.getCalories());
            productEntity.setProteins(product.getProteins());
            productEntity.setFats(product.getFats());
            productEntity.setCarbohydrates(product.getCarbohydrates());

            compositionProduct.add(new CompositionDtoParam(productEntity,
                    compositionEntity.getWeight(),
                    (int) (product.getCalories() * ratio),
                    round(product.getProteins() * ratio),
                    round(product.getFats() * ratio),
                    round(product.getCarbohydrates() * ratio)));
        }

        return new RecipeDto(recipeEntity.getUuid(),
                recipeEntity.getDt_create(),
                recipeEntity.getDt_update(),
                recipeEntity.getTitle(),
                compositionProduct,
                recipeEntity.getWeight(),
                round(recipeEntity.getCalories()),
                round(recipeEntity.getProteins()),
                round(recipeEntity.getFats()),
                round(recipeEntity.getCarbohydrates())
        );
    }

    private double round(double data) {
        int count = 100;
        data = Math.round(data * count);
        double dataCompaund = data / count;
        return dataCompaund;
    }

}
