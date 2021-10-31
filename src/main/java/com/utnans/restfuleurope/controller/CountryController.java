package com.utnans.restfuleurope.controller;

import com.utnans.restfuleurope.entity.Country;
import com.utnans.restfuleurope.service.CountryService;
import com.utnans.restfuleurope.utils.CountryCriteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/top/{criteria}")
    public List<Country> getTopCountriesByCriteria(
            @PathVariable CountryCriteria criteria,
            @RequestParam(defaultValue = "10") int count) {
        return countryService.getTopCountries(criteria, count);
    }
}
