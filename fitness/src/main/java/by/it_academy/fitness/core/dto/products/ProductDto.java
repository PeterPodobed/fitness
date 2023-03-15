package by.it_academy.fitness.core.dto.products;


import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.validationProducts.ProductCreateTitleValid;
import by.it_academy.fitness.core.exception.validationProducts.ProductCreateValid;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDto {
    private UUID uuid;
    private LocalDateTime dt_create;
    private LocalDateTime dt_update;
    private String title;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public ProductDto(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update,
                      String title, int weight, int calories, double proteins,
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
        ProductCreateTitleValid.validate(errorResponse, this.title);
        ProductCreateValid.validate(errorResponse, Integer.toString(this.weight));
        ProductCreateValid.validate(errorResponse, Integer.toString(this.calories));
        ProductCreateValid.validate(errorResponse, Double.toString(this.proteins));
        ProductCreateValid.validate(errorResponse, Double.toString(this.fats));
        ProductCreateValid.validate(errorResponse, Double.toString(this.carbohydrates));
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
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
        return "ProductDto{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", title='" + title + '\'' +
                ", weight=" + weight +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                '}';
    }
}
