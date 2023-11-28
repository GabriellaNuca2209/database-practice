package com.gabriella.databases.services.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriella.databases.exceptions.user.UserNotFoundException;
import com.gabriella.databases.models.dtos.UserDTO;
import com.gabriella.databases.models.entities.User;
import com.gabriella.databases.repositories.UserRepository;
import com.gabriella.databases.services.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserServiceValidation userServiceValidation;
    private final EmailService emailService;

    public UserServiceImpl(ObjectMapper objectMapper, UserRepository userRepository, UserServiceValidation userServiceValidation, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.userServiceValidation = userServiceValidation;
        this.emailService = emailService;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userServiceValidation.validateUserAlreadyExists(userDTO);

        User user = objectMapper.convertValue(userDTO, User.class);
        User savedUser = userRepository.save(user);

        UserDTO responseDTO = objectMapper.convertValue(savedUser, UserDTO.class);
        log.info("User {} has been saved.", responseDTO.getFirstName());

        emailService.sendRegistrationEmail(responseDTO.getEmail(), responseDTO.getFirstName(), responseDTO.getId());

        return responseDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        userRepository.findAll().forEach(user -> userDTOList.add(objectMapper.convertValue(user, UserDTO.class)));

        return userDTOList;
    }

    @Override
    public UserDTO getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
        log.info("Found user with id {}", user.getId());

        return objectMapper.convertValue(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
        updateEntity(user, userDTO);

        User savedUpdatedUser = userRepository.save(user);

        return objectMapper.convertValue(savedUpdatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private void updateEntity(User user, UserDTO userDTO) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
    }
}
