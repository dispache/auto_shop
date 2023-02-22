package com.company.evgeniy.auto_shop.auth;

import com.company.evgeniy.auto_shop.auth.dto.AuthDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("auth")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> auth(@RequestBody AuthDto authDto) {
        Map<String, Object> response = this.authService.auth(authDto.getEmail(), authDto.getPassword());
        if ( response.containsKey("user") ) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
