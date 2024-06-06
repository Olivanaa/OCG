package br.com.fiap.oceanGuardian.validation;

import br.com.fiap.oceanGuardian.enums.NivelGravidadeEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NivelGravidadeValidator implements ConstraintValidator<NivelGravidade, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            NivelGravidadeEnum.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
