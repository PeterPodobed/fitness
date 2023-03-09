package by.it_academy.fitness.core.dto.products;

import java.util.List;

public class RecipeCreateDto {
    private String title;
    public List<CompositionDto> composition;

    public RecipeCreateDto(String title, List<CompositionDto> composition) {
        this.title = title;
        this.composition = composition;
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
