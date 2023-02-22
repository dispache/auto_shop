package com.company.evgeniy.auto_shop.config;

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

    @Autowired
    UsersService usersService;

    @Value("${jwt.secret_key}")
    String jwtSecretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
            auth.anyRequest().permitAll();
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
        AdminRoleAuthFilter adminRoleAuthFilter = new AdminRoleAuthFilter();
        adminRoleAuthFilter.setJwtSecretKey(jwtSecretKey);
        FilterRegistrationBean<AdminRoleAuthFilter> filter = new FilterRegistrationBean<>(adminRoleAuthFilter);
        filter.addUrlPatterns("/api/autos/create-auto", "/api/autos/remove-auto");
        return filter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
