package org.example.userservice.controllers;

import org.example.userservice.dtos.SignupRequestDto;
import org.example.userservice.dtos.UserDto;
import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.models.User;
import org.example.userservice.services.interfaces.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // sign-up
    // login
    // sign-out
    // validate

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistsException {
        Optional<UserDto> userDto = this.authService.signUp(signupRequestDto.getEmail(), signupRequestDto.getPassword());
        return new ResponseEntity<>(userDto.get(), HttpStatus.OK);
    }

}
