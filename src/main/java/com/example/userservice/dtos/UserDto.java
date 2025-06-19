package com.example.userservice.dtos;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;

    public static UserDto from(User user) {
        if(user == null) {
            return null;
        }

        UserDto userdto = new UserDto();
        userdto.setName(user.getName());
        userdto.setEmail(user.getEmail());

        return userdto;
    }
}
