package org.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice.models.Role;

import java.util.Set;

@Setter
@Getter
public class UserDto {
    private String email;
    private Set<Role> roles;
}
