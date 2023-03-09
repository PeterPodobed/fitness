package by.it_academy.fitness.core.dto.products;

import java.util.UUID;

public class CompositionDto {
    private UUID uuidProduct;
    private int weight;

    public CompositionDto(UUID uuid, int weight) {
        this.uuidProduct = uuid;
        this.weight = weight;
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
