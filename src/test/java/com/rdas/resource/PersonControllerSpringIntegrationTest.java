package com.rdas.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdas.model.Person;
import com.rdas.service.InMemoryPersonService;
import com.rdas.testConfig.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by rdas on 22/04/2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class PersonControllerSpringIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private InMemoryPersonService inMemoryPersonService;

    private MockMvc mockMvc;

    private List<Person> persons;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new PersonController(inMemoryPersonService)).build();
        persons = new ArrayList<>();
    }

    @Test
    public void assertThatGetPersonIfIdFound() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("/personbyid/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        //TODO :
//        Optional<Person> actual = mapper.readValue(response.getContentAsString(), new TypeReference<Optional<Person>>() {
//        });
//        System.out.println(actual);
    }
}