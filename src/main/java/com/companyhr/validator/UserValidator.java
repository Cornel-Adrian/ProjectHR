package com.companyhr.validator;


import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.service.EmployeeCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Methods that validate the user input data for login and registration purposes
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    private EmployeeCredentialsService employeeCredentialsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return EmployeeCredentials.class.equals(aClass);
    }

    /**
     * @param o      the received object from the registration forms
     * @param errors the generated errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        EmployeeCredentials user = (EmployeeCredentials) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (employeeCredentialsService.findByUsername(user.getUsername())) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.jobId");
        }
    }

    /**
     * Used for the login process because there is no password confirm in login from
     * @param o Received param from the login form
     * @param errors the generated errors
     */
    public void validatelogin(Object o, Errors errors) {
        EmployeeCredentials user = (EmployeeCredentials) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
            return;
        }
        if (!employeeCredentialsService.findByUsername(user.getUsername())) {
            errors.rejectValue("username", "Duplicate.userForm.username");
            return;
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
            return;
        }

    }
}