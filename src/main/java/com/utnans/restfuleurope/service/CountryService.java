package com.utnans.restfuleurope.service;

import com.utnans.restfuleurope.utils.CountryCriteria;
import com.utnans.restfuleurope.entity.Country;
import com.utnans.restfuleurope.exception.NoResponseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final String endpoint =
            "https://restcountries.com/v2/regionalbloc/eu?fields=name,capital,currencies,population,area";

    private final RestTemplate restTemplate;

    public CountryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Country> getTopCountries(CountryCriteria criteria, int count) {
        Comparator<Country> comparator = getComparator(criteria);

        return fetchData()
                .stream()
                .sorted(comparator)
                .limit(count)
                .collect(Collectors.toList());
    }

    private Comparator<Country> getComparator(CountryCriteria criteria) {

        Comparator<Country> comparator;
        switch (criteria) {
            case AREA:
                comparator = Comparator.comparing(Country::getArea);
                break;
            case DENSITY:
                comparator = Comparator.comparing(Country::getDensity);
                break;
            case POPULATION:
                comparator = Comparator.comparing(Country::getPopulation);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported comparison criteria");
        }

        return comparator.reversed();
    }

    private List<Country> fetchData() {
        Country[] countries = restTemplate
                .getForEntity(endpoint, Country[].class)
                .getBody();

        if (countries == null) {
            throw new NoResponseException();
        }
        return Arrays.asList(countries);
    }

}
