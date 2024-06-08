package org.example.userservice.services;

import org.example.userservice.dtos.UserDto;
import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.models.User;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public Optional<UserDto> signUp(String email, String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException("user with email " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(this.bCryptPasswordEncoder.encode(password));

        User savedUser = this.userRepository.save(user);

        return Optional.of(ConvertEntityToDto.convertUserToUserDto(savedUser));

    }
}
