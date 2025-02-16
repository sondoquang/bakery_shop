package com.stlang.bakery_shop.services.validations;


import com.stlang.bakery_shop.dto.RegisterDTO;
import com.stlang.bakery_shop.services.iservices.IUserService;
import org.springframework.stereotype.Service;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    private final IUserService userService;

    public RegisterValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Check if password fields match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Passwords do not match !")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Check email //
        if (userService.findByEmail(user.getEmail()) != null) {
            context.buildConstraintViolationWithTemplate("Email already exists !")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Check phone //
        if (userService.findByPhoneNumber(user.getPhone()) != null) {
            context.buildConstraintViolationWithTemplate("Phone number already exists !")
                    .addPropertyNode("phone")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Additional validations can be added here

        return valid;
    }

}
