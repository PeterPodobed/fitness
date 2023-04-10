package by.it_academy.fitness.web.controllers;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.RecipeCreateDto;
import by.it_academy.fitness.core.dto.products.RecipeDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.service.products.api.IRecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {
    private final IRecipeService iRecipeService;

    public RecipeController(IRecipeService service) {
        this.iRecipeService = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<?> createRecipe(@RequestBody @Validated RecipeCreateDto recipeCreate)
            throws SingleErrorResponse, MultipleErrorResponse {
        iRecipeService.create(recipeCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<PageDto<RecipeDto>> getPage(
            @RequestParam(name = "number", required = false, defaultValue = "0") int number,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size) throws MultipleErrorResponse {
        return ResponseEntity.status(HttpStatus.OK).body(iRecipeService.getPage(number, size));
    }

    @PutMapping(path = "{uuid}/dt_update/{dt_update}")
    protected ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                       @PathVariable("dt_update") LocalDateTime dt_update,
                                       @RequestBody RecipeCreateDto recipe) throws SingleErrorResponse, MultipleErrorResponse {
        iRecipeService.updateRecipe(uuid, dt_update, recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}