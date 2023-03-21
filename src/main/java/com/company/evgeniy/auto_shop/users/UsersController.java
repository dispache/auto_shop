package com.company.evgeniy.auto_shop.users;

import com.company.evgeniy.auto_shop.users.dto.CreateUserDto;
import com.company.evgeniy.auto_shop.users.dto.UpdateUserDto;
import com.company.evgeniy.auto_shop.users.entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController()
@RequestMapping("/api/users")
@Validated
public class UsersController {

    UsersService usersService;

    UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    public Iterable<UserEntity> getUsers() {
        return this.usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable("id") int userId) {
        return this.usersService.getUserById(userId);
    }

    @PostMapping("create-user")
    public UserEntity createUser(
            @Valid @RequestAttribute("dto") CreateUserDto createUserDto,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile
    ) throws ParseException, IOException {
        if ( avatarFile != null ) {
            createUserDto.setAvatar(avatarFile.getBytes());
        }
        return this.usersService.createUser(createUserDto);
    }

    @DeleteMapping("remove-user/{id}")
    public void removeUser(@PathVariable("id") int userId) {
        this.usersService.removeUser(userId);
    }

    @PutMapping("update-user/{id}")
    public UserEntity updateUser(@PathVariable("id") int userId, @RequestBody UpdateUserDto updateUserDto) throws ParseException {
        return this.usersService.updateUser(userId, updateUserDto);
    }

}

