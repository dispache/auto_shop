package com.company.evgeniy.auto_shop.config;

import com.company.evgeniy.auto_shop.autos.dto.CreateAutoDto;
import com.company.evgeniy.auto_shop.common.interceptors.JsonToDtoMappingInterceptor;
import com.company.evgeniy.auto_shop.users.dto.CreateUserDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JsonToDtoMappingInterceptor<CreateAutoDto>(CreateAutoDto.class)).addPathPatterns("/api/autos/create-auto");
        registry.addInterceptor(new JsonToDtoMappingInterceptor<CreateUserDto>(CreateUserDto.class)).addPathPatterns("/api/users/create-user");
    }
    public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
    }

}
