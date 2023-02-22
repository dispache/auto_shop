package com.company.evgeniy.auto_shop.utils;

import org.modelmapper.ModelMapper;

import java.text.ParseException;

public class MappingUtil {

    private ModelMapper modelMapper = new ModelMapper();

    public <T, K> T mapDtoToEntity(K dto, Class<T> entityClass) throws ParseException {
        T entity = this.modelMapper.map(dto, entityClass);
        return entity;
    }
}