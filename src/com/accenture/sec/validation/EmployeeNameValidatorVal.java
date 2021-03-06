package com.accenture.sec.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=EmployeeNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeNameValidatorVal {
String message() default "{EmployeeNameValidatorVal}";
Class<?>[] groups() default{};
Class<? extends Payload>[] payload() default {};
}
