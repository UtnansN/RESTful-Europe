package com.utnans.restfuleurope;

import com.utnans.restfuleurope.entity.Country;
import com.utnans.restfuleurope.service.CountryService;
import com.utnans.restfuleurope.utils.CountryCriteria;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTests {

    @InjectMocks
    CountryService service;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void testGetTopCountries_byArea_excludeSmallest() {
        // Arrange
        when(restTemplate.getForEntity(anyString(), eq(Country[].class)))
                .thenReturn(new ResponseEntity<>(createMockData(), HttpStatus.OK));

        // Act
        List<Country> countries = service.getTopCountries(CountryCriteria.AREA, 10);

        // Assert
        assertEquals(10, countries.size(), "Function should return 10 countries");
        assertFalse(countries.stream()
                .anyMatch(p -> p.getName().equals("SmallLand") || p.getName().equals("GoodEnoughLand")),
                "Results should not contain smallest area countries");

        long previous = countries.get(0).getArea();
        for (Country country : countries) {
            assertTrue(country.getArea() <= previous, "Countries should be ordered by area in desc. order");
            previous = country.getArea();
        }
    }

    @Test
    public void testGetTopCountries_byPopulation_excludeSmallest() {
        // Arrange
        when(restTemplate.getForEntity(anyString(), eq(Country[].class)))
                .thenReturn(new ResponseEntity<>(createMockData(), HttpStatus.OK));

        // Act
        List<Country> countries = service.getTopCountries(CountryCriteria.POPULATION, 10);

        // Assert
        assertEquals(10, countries.size(), "Function should return 10 countries");
        assertFalse(countries.stream()
                        .anyMatch(p -> p.getName().equals("SmallLand") || p.getName().equals("LargeLand")),
                "Results should not contain smallest population countries");

        long previous = countries.get(0).getPopulation();
        for (Country country : countries) {
            assertTrue(country.getPopulation() <= previous, "Countries should be ordered by population in desc. order");
            previous = country.getPopulation();
        }
    }

    @Test
    public void testGetTopCountries_byDensity_excludeSmallest() {
        // Arrange
        when(restTemplate.getForEntity(anyString(), eq(Country[].class)))
                .thenReturn(new ResponseEntity<>(createMockData(), HttpStatus.OK));

        // Act
        List<Country> countries = service.getTopCountries(CountryCriteria.DENSITY, 10);

        // Assert
        assertEquals(10, countries.size(), "Function should return 10 countries");
        assertFalse(countries.stream()
                        .anyMatch(p -> p.getName().equals("LargeLand") || p.getName().equals("SmallerLand")),
                "Results should not contain smallest density countries");

        double previous = countries.get(0).getDensity();
        for (Country country : countries) {
            assertTrue(country.getDensity() <= previous, "Countries should be ordered by density in desc. order");
            previous = country.getDensity();
        }
    }

    private Country[] createMockData() {
        return new Country[]{
                createCountry("SmallLand", 1000, 250), // Density: 0.25
                createCountry("LargeLand", 5000, 100), // Density: 0.02
                createCountry("GoodEnoughLand", 2000, 1000), // Density: 0.5
                createCountry("NotLastLand", 10000, 2500), // Density: 0.25
                createCountry("RunningOutOfIdeasLand", 3000, 2000), // Density: 0.67
                createCountry("MediumLand", 2500, 600), // Density: 0.24
                createCountry("MediumRareLand", 6600, 2400), // Density: 0.36
                createCountry("GreatLand", 12000, 6000), // Density: 0.5
                createCountry("NotDisneyLand", 2600, 3000), // Density: 1.15
                createCountry("SmallerLand", 15000, 2400), // Density: 0.16
                createCountry("GreenLand", 6000, 2800), // Density: 0.47
                createCountry("IceLand", 5500, 4200) // Density: 0.76
        };
    }

    private Country createCountry(String name, int area, int population) {
        Country country = new Country();
        country.setName(name);
        country.setArea(area);
        country.setPopulation(population);
        return country;
    }
}
