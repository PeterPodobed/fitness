package by.it_academy.fitness.service.products.api;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.ProductCreateDto;
import by.it_academy.fitness.core.dto.products.RecipeCreateDto;
import by.it_academy.fitness.core.dto.products.RecipeDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IRecipeService {
    void create(RecipeCreateDto recipeCreate) throws SingleErrorResponse;

    PageDto<RecipeDto> getPage(int number, int size) throws MultipleErrorResponse;

    void updateRecipe(UUID uuid, LocalDateTime dt_update, RecipeCreateDto recipeCreateDto) throws SingleErrorResponse;
}
