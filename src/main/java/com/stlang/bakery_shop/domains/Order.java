package com.stlang.bakery_shop.domains;

import com.stlang.bakery_shop.domains.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 6, max = 100, message = "FullName must greater than 5 character and less than 101 character !")
    private String fullname;

    @Email(message = "Email incorrect format !")
    private String email;

    @Size(min = 10, max = 20, message = "Phone number must be greater than 9 and less than 21 character !")
    private String phoneNumber;

    @NotBlank(message = "Address is not empty !")
    private String address;
    private String note;

    @Temporal(TemporalType.DATE)
    @Builder.Default
    private Date orderDate = new Date();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private long totalMoney;

    @NotNull
    private boolean isActive;
    @NotBlank (message = "Shipping method is not empty !")
    private String shippingMethod;
    @NotBlank (message = "Shipping address is not empty !")
    private String shippingAddress;
    @NotBlank (message = "Shipping date is not empty !")
    private String shippingDate;
    @NotBlank (message = "Payment method is not empty !")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
