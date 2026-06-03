package com.utibunna.libreriaChat.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.hv.ISBNValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidISBN {

    String message() default "Formato ISBN inválido";

    Class<?>[] groups() default {};
    Class<? extends Payload >[] payload() default  {};
}
