package com.food_ordering_app.models;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne // many order can have the same address because many orders can be delivered to a single address. One ordered can be associated with a single address only.
    private Address deliveryAddress;

    @OneToMany // one order can have multiple order items and multiple order items can be there in a single order.
    private List<OrderItem> items;

    // @Column(name = "payment")
    // private Payment payment;

    @Column(name = "total_item")
    private Integer totalItem;

    @Column(name = "total_price")
    private Double totalPrice;
}

