package org.example.userservice.services.interfaces;

import org.example.userservice.dtos.UserDto;
import org.example.userservice.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public interface AuthService {
    Optional<UserDto> signUp(String email, String password) throws UserAlreadyExistsException;
}
