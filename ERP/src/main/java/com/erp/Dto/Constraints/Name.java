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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@NotEmpty(message = "Username can not be null or blank")
@NotBlank(message = "Username can not be blank")
@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain Alphabets,Number and Underscore")

public @interface Name {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Invalid userName !!";
}
