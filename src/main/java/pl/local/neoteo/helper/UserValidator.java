package pl.local.neoteo.helper;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.local.neoteo.entity.User;


public class UserValidator implements Validator {
    EmailValidator emailValidator = EmailValidator.getInstance();
    RegexValidator telephoneNumberValidator = new RegexValidator("\\d{3}-\\d{3}-\\d{3}");

    @Override
    public boolean supports(Class clazz) { return User.class.isAssignableFrom(clazz); }


    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validation.firstNameRequired");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "validation.lastNameRequired");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.emailNameRequired");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephoneNumber", "validation.telephoneNumberRequired");

        if(errors.getErrorCount() == 0) {
            if(!emailValidator.isValid(((User)o).getEmail())) {
                errors.rejectValue("email", "validation.emailInvalid");
            }
            if(!telephoneNumberValidator.isValid(((User)o).getPhone())) {
                errors.rejectValue("telephoneNumber", "validation.telephoneNumberInvalid");
            }
        }
    }
}
