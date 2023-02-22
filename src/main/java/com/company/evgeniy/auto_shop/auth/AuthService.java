package com.company.evgeniy.auto_shop.auth;

import com.company.evgeniy.auto_shop.users.UsersService;
import com.company.evgeniy.auto_shop.users.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Value("${jwt.secret_key}")
    String jwtSecretKey;

    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Map<String, Object> auth(String email, String password) {
        UserEntity user = this.usersService.findUserByEmail(email);
        Map<String, Object> responseMap = new HashMap<>();
        if ( user != null ) {
            boolean isPasswordValid = bCryptPasswordEncoder.matches(password, user.getPassword());
            if ( isPasswordValid ) {
                responseMap.put("user", user);
                long timestamp = System.currentTimeMillis();
                String token = Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                        .setIssuedAt(new Date(timestamp))
                        .setExpiration(new Date(timestamp + 7*1000*60*60*24))
                        .claim("email", user.getEmail())
                        .claim("firstName", user.getFirstName())
                        .claim("lastName", user.getLastName())
                        .claim("role", user.getRole())
                        .compact();
                responseMap.put("Access token", token);
            } else {
                responseMap.put("Error", "Wrong credentials");
            }
        } else {
            responseMap.put("Error", "User not found");
        }
        return responseMap;
    }
}
