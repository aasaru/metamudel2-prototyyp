package ee.smit.metamudel2.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    protected Product() {

    }

    public Product(String brand, String category, String name, String flavour, Double caloriesPer100Gram, Double weight) {
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.flavour = flavour;
        this.caloriesPer100Gram = caloriesPer100Gram;
        this.weight = weight;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name="brand", nullable=false)
    private String brand;
    @Column(name="category", nullable=false)
    private String category;
    @Column(name="name", nullable=false)
    private String name;
    @Column(name="flavour")
    private String flavour;
    @Column(name="calories_per_100_gram", nullable=false)
    private Double caloriesPer100Gram;
    @Column(name="weight_gram", nullable=false)
    private Double weight;

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getFlavour() {
        return flavour;
    }

    public Double getCaloriesPer100Gram() {
        return caloriesPer100Gram;
    }

    public Double getWeight() {
        return weight;
    }
}
