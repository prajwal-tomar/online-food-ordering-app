package com.food_ordering_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredients_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne // Many ingredients can be there inside a category. For example: Sauce category can have various sauces inside it.
    private IngredientCategory category;

    @JsonIgnore
    @ManyToOne // Many ingredients can be there inside a restaurant
    private Restaurant restaurant;

    @Column(name = "in_stock")
    private Boolean inStock = true;
}
