package by.it_academy.fitness.core.dto.products;

import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.validationRecipe.RecipeCreateTitleValid;
import by.it_academy.fitness.core.exception.validationRecipe.RecipeCreateValid;
import by.it_academy.fitness.dao.entity.products.CompositionEntity;
import by.it_academy.fitness.service.convertion.json.LocalDateTimeToLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RecipeDto {
    private UUID uuid;
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    private LocalDateTime dt_create;
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    private LocalDateTime dt_update;
    private String title;
    private List<CompositionDtoParam> composition;
    private double weight;
    private double calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public RecipeDto(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update,
                     String title, List<CompositionDtoParam> composition, double weight,
                     double calories, double proteins, double fats, double carbohydrates)
            throws MultipleErrorResponse {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.title = title;
        this.composition = composition;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        validate();
    }

    public RecipeDto(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update,
                     String title, double weight, double calories, double proteins,
                     double fats, double carbohydrates) throws MultipleErrorResponse {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        validate();
    }

    public void validate() throws MultipleErrorResponse {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("Данные отсутствуют");
        RecipeCreateTitleValid.validate(errorResponse, this.title);
        RecipeCreateValid.validate(errorResponse, Double.toString(this.weight));
        RecipeCreateValid.validate(errorResponse, Double.toString(this.calories));
        RecipeCreateValid.validate(errorResponse, Double.toString(this.proteins));
        RecipeCreateValid.validate(errorResponse, Double.toString(this.fats));
        RecipeCreateValid.validate(errorResponse, Double.toString(this.carbohydrates));
        if (!errorResponse.getErrors().isEmpty()) {
            throw errorResponse;
        }
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public LocalDateTime getDt_update() {
        return dt_update;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CompositionDtoParam> getComposition() {
        return composition;
    }

    public void setComposition(List<CompositionDtoParam> composition) {
        this.composition = composition;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    @Override
    public String toString() {
        return "RecipeDto{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", title='" + title + '\'' +
                ", composition=" + composition +
                ", weight=" + weight +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                '}';
    }
}
