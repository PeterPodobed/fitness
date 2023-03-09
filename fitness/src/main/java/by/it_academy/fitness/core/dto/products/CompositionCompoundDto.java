package by.it_academy.fitness.core.dto.products;

import java.util.UUID;

public class CompositionCompoundDto {
    private UUID uuidRecipe;
    private UUID uuidProduct;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public CompositionCompoundDto(UUID uuidRecipe, UUID uuidProduct, int weight,
                                  int calories, double proteins, double fats, double carbohydrates) {
        this.uuidRecipe = uuidRecipe;
        this.uuidProduct = uuidProduct;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public CompositionCompoundDto() {
    }

    public UUID getUuidRecipe() {
        return uuidRecipe;
    }

    public void setUuidRecipe(UUID uuidRecipe) {
        this.uuidRecipe = uuidRecipe;
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
}
