package by.it_academy.fitness.service.convertion.recipe.api;

import by.it_academy.fitness.core.dto.products.RecipeDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.products.RecipeEntity;

public interface IRecipeEntityToDto {

    RecipeDto convertRecipeEntityToDto(RecipeEntity recipeEntity) throws MultipleErrorResponse;
}
