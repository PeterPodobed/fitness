package by.it_academy.fitness.core.dto.products;

import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.validationRecipe.RecipeCreateTitleValid;
import by.it_academy.fitness.core.exception.validationRecipe.RecipeCreateValid;

import java.util.UUID;

public class CompositionDto {
    private UUID uuidProduct;
    private int weight;

    public CompositionDto(UUID uuid, int weight) {
        this.uuidProduct = uuid;
        this.weight = weight;
    }

    public void validate() throws MultipleErrorResponse {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("Данные отсутствуют");
        RecipeCreateValid.validate(errorResponse, Integer.toString(this.weight));
        if (!errorResponse.getErrors().isEmpty()) {
            throw errorResponse;
        }
    }

    public UUID getUuidProduct() {
        return uuidProduct;
    }

    public void setUuidProduct(UUID uuidProduct) {
        this.uuidProduct = uuidProduct;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "CompositionDto{" +
                "uuid=" + uuidProduct +
                ", weight=" + weight +
                '}';
    }
}
