package com.companyhr.validator;


import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.service.EmployeeCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeId", "NotEmpty");
        if (user.getEmployeeId().toString().length() < 1 || user.getEmployeeId().toString().length() > 32) {
            errors.rejectValue("employeeId", "Size.userForm.employee_id");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobId", "NotEmpty");
        if (user.getJobId().toString().length() < 1 || user.getJobId().toString().length() > 32) {
            errors.rejectValue("jobId", "Size.userForm.employeeId");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.jobId");
        }
    }

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
       /* EmployeeCredentials employeeCredentials= employeeCredentialsRepository.findByUsername(user.getUsername());

        if (!employeeCredentials.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.jobId");
        }*/
    }
}