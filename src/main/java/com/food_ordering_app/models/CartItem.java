package com.food_ordering_app.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many food items can be there inside a single cart item.
    @JsonIgnore
    private Cart cart;

    @ManyToOne // Many cart items can have the same food.
    private Food food;

    @Column(name = "quantity")
    private Integer quantity;

    @ElementCollection
    @Column(name = "ingredient")
    private List<String> ingredients;

    @Column(name = "total_price")
    private Double totalPrice;
}
