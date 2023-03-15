package by.it_academy.fitness.service.convertion.recipe;

import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.dto.products.RecipeDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.dao.entity.products.RecipeEntity;
import by.it_academy.fitness.service.convertion.products.api.IProductEntityToDto;
import by.it_academy.fitness.service.convertion.recipe.api.IRecipeEntityToDto;
import org.springframework.stereotype.Component;

@Component
public class RecipeEntityToDto implements IRecipeEntityToDto {
    @Override
    public RecipeDto convertRecipeEntityToDto(RecipeEntity recipeEntity) throws MultipleErrorResponse {
        return new RecipeDto(recipeEntity.getUuid(),
                recipeEntity.getDt_create(),
                recipeEntity.getDt_update(),
                recipeEntity.getTitle(),
                recipeEntity.getWeight(),
                round(recipeEntity.getCalories()),
                round(recipeEntity.getProteins()),
                round(recipeEntity.getFats()),
                round(recipeEntity.getCarbohydrates())
        );
    }

    private double round (double data) {
        int count = 100;
        data = Math.round(data * count);
        double dataCompaund = data / count;
        return dataCompaund;
    }
}
