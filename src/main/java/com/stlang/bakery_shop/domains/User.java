package com.stlang.bakery_shop.domains;

import com.stlang.bakery_shop.domains.enums.Role;
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
@Table(name="users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 100, message = "FullName must greater than 5 character and less than 101 character !")
    private String fullname;

    @Size(min = 10, max = 20, message = "Phone number must be greater than 9 and less than 21 character !")
    private String phoneNumber;

    @Email(message = "Email don't match format !")
    private String email;

    @NotBlank(message = "Address is not empty !")
    private String address;
    @Size(min = 6, message = "Password must greater than 5 character !")
    private String password;

    @Builder.Default
    private Date createAt = new Date();
    @Builder.Default
    private Date updateAt = new Date();

    @NotNull
    private boolean isActive;

    @Temporal(TemporalType.DATE)
    private Date dayOfBirth;
    private Long facebookId;
    private Long googleId;

    @Enumerated(EnumType.STRING)
    private Role role;


}
