package com.company.evgeniy.auto_shop.config;

import com.company.evgeniy.auto_shop.auth.MyOAuth2UserService;
import com.company.evgeniy.auto_shop.auth.TokenService;
import com.company.evgeniy.auto_shop.security.filters.AdminRoleAuthFilter;
import com.company.evgeniy.auto_shop.users.UsersService;
import com.company.evgeniy.auto_shop.users.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private UsersService usersService;
    private MyOAuth2UserService myOAuth2UserService;
    private final TokenService tokenService;

    @Value("${jwt.secret_key}")
    private String jwtSecretKey;

    public WebSecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    private void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    private void setMyOAuth2UserService(MyOAuth2UserService myOAuth2UserService) {
        this.myOAuth2UserService = myOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
            try {
                auth
                        .anyRequest().permitAll()
                        .and()
                        .oauth2Login()
                        .userInfoEndpoint().userService(this.myOAuth2UserService)
                        .and()
                        .redirectionEndpoint().baseUri("/auth/google/callback")
                        .and()
                        .defaultSuccessUrl("/auth/google/callback/user");
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                UserEntity userEntity = usersService.findUserByEmail(email);
                if ( userEntity == null ) {
                    throw new UsernameNotFoundException("User with email " + email + " not found");
                }
                return new User(
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole()))
                );
            }
        };
    }

    @Bean
    public FilterRegistrationBean<AdminRoleAuthFilter> filterRegistrationBean() {
        AdminRoleAuthFilter adminRoleAuthFilter = new AdminRoleAuthFilter(this.tokenService);
        adminRoleAuthFilter.setJwtSecretKey(jwtSecretKey);
        FilterRegistrationBean<AdminRoleAuthFilter> filter = new FilterRegistrationBean<>(adminRoleAuthFilter);
        filter.addUrlPatterns("/api/autos/create-auto", "/api/autos/remove-auto/*", "/api/autos/update-auto/*");
        return filter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
