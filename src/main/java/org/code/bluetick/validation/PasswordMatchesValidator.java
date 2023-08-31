package org.code.bluetick.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.code.bluetick.web.mapstruct.dto.UserDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        if(user.getPassword() == null || user.getMatchingPassword() == null) {
            return false;
        }

        return user.getPassword().equals(user.getMatchingPassword());
    }
}
