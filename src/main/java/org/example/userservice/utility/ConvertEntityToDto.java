package org.example.userservice.utility;

import org.example.userservice.dtos.UserDto;
import org.example.userservice.models.User;

public class ConvertEntityToDto {
    public static UserDto convertUserToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
