package com.utnans.restfuleurope;

import com.utnans.restfuleurope.controller.CountryController;
import com.utnans.restfuleurope.entity.Country;
import com.utnans.restfuleurope.service.CountryService;
import com.utnans.restfuleurope.utils.CountryCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CountryController.class)
public class CountryControllerTests {

    @MockBean
    private CountryService countryService;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTopCountriesByArea_isOk() throws Exception {
        when(countryService.getTopCountries(CountryCriteria.AREA, 10))
                .thenReturn(getCountryStubs("area"));

        mockMvc.perform(get("/top/area"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("areaStub1"))
                .andExpect(jsonPath("$[1].name").value("areaStub2"));
    }

    @Test
    public void testGetTopCountriesByPopulation_isOk() throws Exception {
        when(countryService.getTopCountries(CountryCriteria.POPULATION, 10))
                .thenReturn(getCountryStubs("population"));

        mockMvc.perform(get("/top/population"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("populationStub1"))
                .andExpect(jsonPath("$[1].name").value("populationStub2"));
    }

    @Test
    public void testGetTopCountriesByDensity_isOk() throws Exception {
        when(countryService.getTopCountries(CountryCriteria.DENSITY, 10))
                .thenReturn(getCountryStubs("density"));

        mockMvc.perform(get("/top/density"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("densityStub1"))
                .andExpect(jsonPath("$[1].name").value("densityStub2"));
    }

    private List<Country> getCountryStubs(String name) {
        Country country1 = new Country();
        country1.setName(name + "Stub1");

        Country country2 = new Country();
        country2.setName(name + "Stub2");

        return List.of(country1, country2);
    }
}
