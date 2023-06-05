package com.service;

import java.util.Optional;

import com.model.dto.UserDTO;
import com.service.dao.UserDAO;

public class UserService {

    UserDAO dao;

    public UserService() {
        dao = new UserDAO();
    }

    public Optional<UserDTO> getUser(UserDTO dto) {

        var optionalUser = dao.getConnectedUser(dto.username.get(), dto.password.get());

        return optionalUser.map(user -> new UserDTO()
                .setUsername(user.getUsername())
                .setUserRole(user.getUserRole())

        ).or(Optional::empty);

    }

}
