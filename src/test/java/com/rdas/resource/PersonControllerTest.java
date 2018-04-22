package com.rdas.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdas.model.Person;
import com.rdas.model.PersonType;
import com.rdas.service.InMemoryPersonService;
import com.rdas.testConfig.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
/**
 * Created by rdas on 22/04/2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class PersonControllerTest {

    @MockBean
    private InMemoryPersonService inMemoryPersonService;

    private MockMvc mockMvc;

    private List<Person> persons;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new PersonController(inMemoryPersonService)).build();
        persons = new ArrayList<>();
    }

    @Test
    public void assertThatGetAllReturnsOk() throws Exception {
        //given
        persons.add(Person.builder().id("1").name("Rana").age(40).type(PersonType.MALE).build());
        persons.add(Person.builder().id("2").name("Jennifer").age(40).type(PersonType.FEMALE).build());
        persons.add(Person.builder().id("3").name("Connal").age(100).type(PersonType.GENDER_NEUTRAL).build());
        persons.add(Person.builder().id("4").name("Florence").age(99).type(PersonType.FEMALE).build());
        persons.add(Person.builder().id("5").name("Zack").age(11).type(PersonType.MALE).build());
        given(inMemoryPersonService.getPersons()).willReturn(persons);

        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("/persons")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse()
                ;

                //.andExpect(status().isOk())
                //.andDo(print())

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


    @Test
    public void assertThatGetMalesReturnsOk() throws Exception {
        //given
        PersonType maleType = PersonType.MALE;
        persons.add(Person.builder().id("1").name("Rana Das").age(40).type(PersonType.MALE).build());
        persons.add(Person.builder().id("5").name("Zack Murphy").age(11).type(PersonType.MALE).build());
        given(inMemoryPersonService.getPersons(maleType)).willReturn(persons);

        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("/personsbytype")
                        .param("type", "male")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                //
                .andExpect(jsonPath("$[0].name", is("Rana Das")))
                .andExpect(jsonPath("$[1].name", is("Zack Murphy")))
                //
                .andReturn()
                .getResponse()
                ;

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        ObjectMapper mapper = new ObjectMapper();
        List<Person> actual = mapper.readValue(response.getContentAsString(), new TypeReference<List<Person>>() {});
        assertEquals(2, actual.size());
    }
}