package com.khaledmosharraf.twtms.validations;

import com.khaledmosharraf.twtms.dto.UserRequestDTO;
import com.khaledmosharraf.twtms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequestDTO userRequestDTO = (UserRequestDTO) target;

        // Check if email already exists in the database
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            errors.rejectValue("username", "Duplicate.userRequestDTO.username");
        }
    }
}