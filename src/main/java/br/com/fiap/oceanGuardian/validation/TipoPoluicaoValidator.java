package br.com.fiap.oceanGuardian.validation;

import br.com.fiap.oceanGuardian.enums.TipoPoluicaoEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TipoPoluicaoValidator implements ConstraintValidator<TipoPoluicao, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            TipoPoluicaoEnum.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
