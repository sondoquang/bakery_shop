package com.stlang.bakery_shop.domains;

import com.stlang.bakery_shop.domains.enums.OrderStatus;
import com.stlang.bakery_shop.domains.enums.PaymentMethod;
import com.stlang.bakery_shop.domains.enums.PaymentStatus;
import com.stlang.bakery_shop.domains.enums.ShippingMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 100, message = "FullName must greater than 5 character and less than 101 character !")
    private String fullname;

    @Email(message = "Email incorrect format !")
    private String email;

    @Size(min = 10, max = 20, message = "Phone number must be greater than 9 and less than 21 character !")
    private String phoneNumber;

    private String note;

    @Temporal(TemporalType.DATE)
    @Builder.Default
    private Date orderDate = new Date();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @NotNull
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod = ShippingMethod.Express_Shipping;

    @NotBlank (message = "Shipping address is not empty !")
    @Column(columnDefinition = "NVARCHAR(1000)")
    private String shippingAddress;

    @Builder.Default
    private Date shippingDate = new Date();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.COD;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String paymentRef;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;
}
