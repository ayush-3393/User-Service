package org.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
    private UserDto userDto;
    private String token;
}
