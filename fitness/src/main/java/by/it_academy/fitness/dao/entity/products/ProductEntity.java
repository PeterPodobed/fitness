package by.it_academy.fitness.dao.entity.products;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product", schema = "public")
public class ProductEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dt_create;

    @Column(name = "dt_update")
    @Version
    private LocalDateTime dt_update;

    @Column(name = "title")
    private String title;

    @Column(name = "weight")
    private int weight;

    @Column(name = "calories")
    private int calories;

    @Column(name = "proteins")
    private double proteins;

    @Column(name = "fats")
    private double fats;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    public ProductEntity(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update,
                         String title, int weight, int calories, double proteins,
                         double fats, double carbohydrates) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;

    }

    public ProductEntity(UUID uuid, String title, int weight, int calories,
                         double proteins, double fats, double carbohydrates) {
        this.uuid = uuid;
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public ProductEntity() {

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
        return "ProductEntity{" +
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
