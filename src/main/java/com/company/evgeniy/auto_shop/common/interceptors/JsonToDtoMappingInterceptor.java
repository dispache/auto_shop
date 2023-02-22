package com.company.evgeniy.auto_shop.common.interceptors;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JsonToDtoMappingInterceptor<T> implements HandlerInterceptor {

    Class<T> dtoClass;

    public JsonToDtoMappingInterceptor(Class<T> dtoClass) {
        this.dtoClass = dtoClass;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String bodyParam = request.getParameter("body");
        Gson gson = new Gson();
        T dto = gson.fromJson(bodyParam, dtoClass);
        request.setAttribute("dto", dto);
        return true;
    }

}