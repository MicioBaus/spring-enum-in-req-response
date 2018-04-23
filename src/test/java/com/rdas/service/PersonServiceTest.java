package com.rdas.service;

import com.rdas.model.Person;
import com.rdas.model.PersonType;
import com.rdas.testConfig.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rdas on 22/04/2018.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class PersonServiceTest {

    @Autowired
    private InMemoryPersonService inMemoryPersonService;

    @Before
    public void init() {
        assertNotNull(inMemoryPersonService);
    }

    @Test
    public void assertThatGetAllIsAvailable() {
        assertNotNull(inMemoryPersonService.getPersons());
        assertEquals(inMemoryPersonService.getPersons().size(), 5);
    }

    @Test
    public void assertThatZackIsAvailable() {
        assertNotNull(inMemoryPersonService.getPerson("ZACK"));
    }


    @Test
    public void assertThatZackCannotBeAddedAgainIsAvailable() {
        Person p = Person.builder().id(6).name("ZACK").age(45).type(PersonType.MALE).build();
        assertFalse(inMemoryPersonService.addPerson(p));
        assertEquals(inMemoryPersonService.getPersons().size(), 5);
    }

    @Test
    public void assertThatZackExists() {
        int zack = inMemoryPersonService.getPerson("ZACK").size();
        assertEquals(1, zack);
    }
}