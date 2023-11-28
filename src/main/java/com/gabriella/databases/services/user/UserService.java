package com.gabriella.databases.services.user;

import com.gabriella.databases.models.dtos.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUser(long id);
    UserDTO updateUser(UserDTO userDTO, long id);
    void deleteUser(long id);
}
