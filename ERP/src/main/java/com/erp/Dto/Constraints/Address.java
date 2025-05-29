package com.erp.Dto.Constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@NotEmpty(message = "Address can not be null or blank")
@NotBlank(message = "Address can not be blank")
@Pattern(regexp = "^[A-Za-z0-9.,#\\-/ ]{5,100}$")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Address {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Input must be between 5 and 100 characters long and can only contain letters, numbers, spaces, and the following special characters: ., #, -, /.";
}
