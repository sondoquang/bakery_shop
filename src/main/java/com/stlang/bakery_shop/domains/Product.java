package com.stlang.bakery_shop.domains;


import com.stlang.bakery_shop.domains.enums.ProductStatus;
import com.stlang.bakery_shop.domains.enums.Target;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (columnDefinition = "NVARCHAR(350)")
    @NotBlank(message = "Name product is not empty !")
    private String name;

    @Min(value = 50000, message = "Product's price must be greater than 50000 vnd !")
    @NotNull( message = "Product's price must be greater than 50000 vnd !")
    private BigDecimal price;

    private String image;

    @NotBlank(message = "Short Description is not empty !")
    private String shortDesc;

    @NotBlank(message = "Detail Description is not empty !")
    @Column (columnDefinition = "NVARCHAR(MAX)")
    private String detailDesc;

    @NotNull(message = "Target must be select !")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Target target = Target.BIRTHDAY;
    @Builder.Default
    private Date createAt = new Date();
    @Builder.Default
    private Date updateAt = new Date();
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.IN_STOCK;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails; ;

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetails;

}
