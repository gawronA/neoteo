package pl.local.mpark.helper;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.springframework.data.util.Pair;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.local.mpark.entity.AppUser;


public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class clazz) { return AppUser.class.isAssignableFrom(clazz); }


    @Override
    public void validate(Object o, Errors errors) {
        Pair<String, String> passwords = (Pair<String, String>)o;
        if(passwords.getFirst().isEmpty()) errors.rejectValue("password", "validation.passwordRequired");
        if(passwords.getSecond().isEmpty()) errors.rejectValue("password", "validation.passwordRequired");

        if(errors.getErrorCount() == 0) {
            if(!passwords.getFirst().equals(passwords.getSecond())) {
                errors.rejectValue("password", "validation.passwordShouldMatch");
            }
        }
    }
}
