package pl.local.neoteo.helper;

import org.springframework.data.util.Pair;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.local.neoteo.entity.User;


public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class clazz) { return User.class.isAssignableFrom(clazz); }


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
