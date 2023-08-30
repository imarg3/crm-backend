package org.code.bluetick.validation;

import org.code.bluetick.web.mapstruct.dto.UserDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "message.fullName", "Full Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessName", "message.businessName", "Last Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "message.email", "Email ID is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "message.mobile", "Mobile number is required.");
    }
}
