package by.it_academy.fitness.core.dto.products;

import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.validationRecipe.RecipeCreateTitleValid;

import java.util.List;

public class RecipeCreateDto {
    private String title;
    public List<CompositionDto> composition;

    public RecipeCreateDto(String title, List<CompositionDto> composition) throws MultipleErrorResponse {
        this.title = title;
        this.composition = composition;
        validate();
    }

    public void validate() throws MultipleErrorResponse {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("Данные отсутствуют");
        RecipeCreateTitleValid.validate(errorResponse, this.title);
        if (!errorResponse.getErrors().isEmpty()) {
            throw errorResponse;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CompositionDto> getComposition() {
        return composition;
    }

    public void setComposition(List<CompositionDto> composition) {
        this.composition = composition;
    }

    @Override
    public String toString() {
        return "RecipeForCUDto{" +
                "title='" + title + '\'' +
                ", composition=" + composition +
                '}';
    }
}
