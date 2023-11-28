package com.gabriella.databases.services.user;

import com.gabriella.databases.exceptions.user.UserAlreadyExistsException;
import com.gabriella.databases.models.dtos.UserDTO;
import com.gabriella.databases.models.entities.User;
import com.gabriella.databases.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserServiceValidation {

    private final UserRepository userRepository;

    public UserServiceValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserAlreadyExists(UserDTO userDTO) {
        User foundUser = userRepository.findByEmail(userDTO.getEmail());

        if (foundUser != null) {
            throw new UserAlreadyExistsException("User with same email already exists.");
        }
    }
}
