package com.company.evgeniy.auto_shop.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CreateUserDto {
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Incorrect email format")
    String email;

    @NotEmpty(message = "Password cannot be empty")
    String password;

    @NotEmpty(message = "FirstName cannot be empty")
    String firstName;

    @NotEmpty(message = "LastName cannot be empty")
    String lastName;

    String country;
    byte[] avatar;

    public CreateUserDto(String email, String password, String firstName, String lastName, String country, byte[] avatar) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String toString() {
        String result = "Email: " + this.email + "\n" + "Password: " + this.password + "\n" + "FirstName: " +
                this.firstName + "\n" + "LastName: " + this.lastName + "\n" + "Country: " + this.country +
                "\n" + "Avatar: " + this.avatar;
        return result;
    }
}
