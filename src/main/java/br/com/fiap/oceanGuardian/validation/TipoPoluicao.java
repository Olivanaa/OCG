package br.com.fiap.oceanGuardian.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TipoPoluicaoValidator.class)
public @interface TipoPoluicao {

    String message() default "{reporte.tipo}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
