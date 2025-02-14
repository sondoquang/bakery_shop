package com.stlang.bakery_shop.domains;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 0, message = "Product's price must be greater than 0 !")
    private double price;

    @Min(value = 1, message = "Product's price must be greater than 1 !")
    private int numberOfProducts;
    @Min(value = 0, message = "Total money must be greater than 0 !")
    private double total_money;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
