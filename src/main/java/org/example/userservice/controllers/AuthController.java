package org.example.userservice.controllers;

import org.example.userservice.dtos.LoginRequestDto;
import org.example.userservice.dtos.LoginResponseDto;
import org.example.userservice.dtos.SignupRequestDto;
import org.example.userservice.dtos.UserDto;
import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.exceptions.UserDoesNotExistException;
import org.example.userservice.exceptions.WrongPasswordException;
import org.example.userservice.models.User;
import org.example.userservice.services.interfaces.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<?> signUp(@RequestBody SignupRequestDto signupRequestDto){
        try {
            UserDto userDto = this.authService.signUp(signupRequestDto.getEmail(), signupRequestDto.getPassword());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (UserAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        try {
            LoginResponseDto loginResponseDto =
                    this.authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
            headers.add("AUTH_TOKEN", loginResponseDto.getToken());
            return new ResponseEntity<>(loginResponseDto.getUserDto(), headers, HttpStatus.OK);
        }
        catch (UserDoesNotExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (WrongPasswordException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
