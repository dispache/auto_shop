package com.company.evgeniy.auto_shop.auth;

import com.company.evgeniy.auto_shop.auth.dto.AuthDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("google")
    public void googleAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/google/callback/user")
    public ResponseEntity<Map<String, Object>> googleCallback(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        Map<String, Object> response = this.authService.authByGoogle(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
