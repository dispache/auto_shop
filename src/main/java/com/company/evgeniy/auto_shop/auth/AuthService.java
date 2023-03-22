package com.company.evgeniy.auto_shop.auth;

import com.company.evgeniy.auto_shop.users.UsersService;
import com.company.evgeniy.auto_shop.users.entities.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    public AuthService(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }

    public Map<String, Object> auth(String email, String password) {
        UserEntity user = this.usersService.findUserByEmail(email);
        Map<String, Object> responseMap = new HashMap<>();
        if ( user != null ) {
            boolean isPasswordValid = bCryptPasswordEncoder.matches(password, user.getPassword());
            if ( isPasswordValid ) {
                responseMap.put("user", user);
                String token = this.tokenService.generateAccessToken(user);
                responseMap.put("Access token", token);
            } else {
                responseMap.put("Error", "Wrong credentials");
            }
        } else {
            responseMap.put("Error", "User not found");
        }
        return responseMap;
    }

    public Map<String, Object> authByGoogle(String email) {
        UserEntity userEntity = this.usersService.findUserByEmail(email);
        Map<String, Object> responseMap = new HashMap<>();
        String token = this.tokenService.generateAccessToken(userEntity);
        responseMap.put("user", userEntity);
        responseMap.put("Access token", token);
        return responseMap;
    }
}
