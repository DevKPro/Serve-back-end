package pers.leanfeng.score.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {
    String message() default "";
    int min() default 6;
    int max() default 32;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
