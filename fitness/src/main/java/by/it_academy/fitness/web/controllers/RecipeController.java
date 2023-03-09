package by.it_academy.fitness.web.controllers;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.RecipeCreateDto;
import by.it_academy.fitness.core.dto.products.RecipeDto;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.service.products.api.IRecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {
    private final IRecipeService service;

    public RecipeController(IRecipeService service) {
        this.service = service;
    }

    @PostMapping
    public void createRecipe(@RequestBody @Validated RecipeCreateDto recipeCreate) throws SingleErrorResponse {
        service.create(recipeCreate);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<RecipeDto>> getPage(
            @RequestParam(name = "number", required = false, defaultValue = "0") int number,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPage(number, size));
    }


}