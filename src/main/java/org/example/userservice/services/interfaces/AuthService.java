package org.example.userservice.services.interfaces;

import org.example.userservice.dtos.LoginResponseDto;
import org.example.userservice.dtos.UserDto;
import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.exceptions.UserDoesNotExistException;
import org.example.userservice.exceptions.WrongPasswordException;

import java.util.Optional;

public interface AuthService {
    UserDto signUp(String email, String password) throws UserAlreadyExistsException;
    LoginResponseDto login(String email, String password) throws UserDoesNotExistException, WrongPasswordException;
}
