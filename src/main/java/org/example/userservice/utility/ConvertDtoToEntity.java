package org.example.userservice.utility;

import org.example.userservice.dtos.UserDto;
import org.example.userservice.models.User;

public class ConvertDtoToEntity {
    public static User convertUserDtoToUserEntity(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
        return user;
    }
}
