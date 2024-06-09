package org.example.userservice.services;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.userservice.dtos.LoginResponseDto;
import org.example.userservice.dtos.UserDto;
import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.exceptions.UserDoesNotExistException;
import org.example.userservice.exceptions.WrongPasswordException;
import org.example.userservice.models.Session;
import org.example.userservice.models.User;
import org.example.userservice.models.enums.SessionStatus;
import org.example.userservice.repositories.SessionRepository;
import org.example.userservice.repositories.UserRepository;
import org.example.userservice.services.interfaces.AuthService;
import org.example.userservice.utility.ConvertEntityToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImplementation(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public UserDto signUp(String email, String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException("user with email " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(this.bCryptPasswordEncoder.encode(password));

        User savedUser = this.userRepository.save(user);

        return ConvertEntityToDto.convertUserToUserDto(savedUser);

    }

    @Override
    public LoginResponseDto login(String email, String password) throws UserDoesNotExistException, WrongPasswordException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserDoesNotExistException("user with email " + email + " does not exist!");
        }
        User user = userOptional.get();

        if (!this.bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new WrongPasswordException("password doesn't match!");
        }

        String token = RandomStringUtils.randomAscii(20);

        Session session = new Session();
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);

        this.sessionRepository.save(session);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUserDto(ConvertEntityToDto.convertUserToUserDto(user));
        loginResponseDto.setToken(token);

        return loginResponseDto;
    }
}
