package com.food_ordering_app.dto;

import java.util.List;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RestaurantDTO {

    private String name;

    private List<String> images;

    private String description;

    private Long id;

}
