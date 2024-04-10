package com.food_ordering_app.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    // a food category can have various food inside it. For example: Pizza category
    // can have multiple types of Pizzas
    private FoodCategory foodCategory;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    @Column(name = "available")
    private Boolean available;

    @ManyToOne /// inside a single restaurant there are multiple food.
    private Restaurant restaurant;

    @Column(name = "is_vegetarian")
    private Boolean isVegetarian;

    @Column(name = "is_seasonal")
    private Boolean isSeasonal;

    @ManyToMany
    @Column(name = "ingredient")
    private List<IngredientsItem> ingredients;

    @Column(name = "creation_date")
    private Date creationDate;
}
