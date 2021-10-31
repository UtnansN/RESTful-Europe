package com.utnans.restfuleurope.utils;

import com.utnans.restfuleurope.exception.CriteriaNotFoundException;
import org.springframework.core.convert.converter.Converter;

public class CriteriaEnumConverter implements Converter<String, CountryCriteria> {

    @Override
    public CountryCriteria convert(String source) {
        try {
            return CountryCriteria.valueOf(source.toUpperCase());
        }
        catch (IllegalArgumentException ex) {
            throw new CriteriaNotFoundException();
        }
    }
}
