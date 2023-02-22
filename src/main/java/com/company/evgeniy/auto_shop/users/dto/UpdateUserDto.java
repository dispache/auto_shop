package com.company.evgeniy.auto_shop.users.dto;

import com.company.evgeniy.auto_shop.users.dto.CreateUserDto;

public class UpdateUserDto extends CreateUserDto {
    String email;
    String password;
    String firstName;
    String lastName;
    String country;
    byte[] avatar;

    public UpdateUserDto(String email, String password, String firstName, String lastName, String country, byte[] avatar) {
        super(email, password, firstName, lastName, country, avatar);
    }
}
