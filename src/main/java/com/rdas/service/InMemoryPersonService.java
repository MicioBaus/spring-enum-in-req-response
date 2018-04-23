package com.rdas.service;

import com.rdas.model.Person;
import com.rdas.model.PersonType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InMemoryPersonService {
    private List<Person> persons;

    @PostConstruct
    public void init() {
        log.info("Init data");
        persons = new ArrayList<>();
        persons.add(Person.builder().id(1).name("Rana").age(40).type(PersonType.MALE).build());
        persons.add(Person.builder().id(2).name("Jennifer").age(40).type(PersonType.FEMALE).build());
        persons.add(Person.builder().id(3).name("Connal").age(100).type(PersonType.GENDER_NEUTRAL).build());
        persons.add(Person.builder().id(4).name("Florence").age(99).type(PersonType.FEMALE).build());
        persons.add(Person.builder().id(5).name("Zack").age(11).type(PersonType.MALE).build());
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Person> getPersons(Person personToSearch) {
        return persons.stream().filter(p -> p.getType() == personToSearch.getType())
                .filter(p -> p.getId() == personToSearch.getId())
                .filter(p -> p.getName().equalsIgnoreCase(personToSearch.getName()))
                .collect(Collectors.toList());
    }

    public List<Person> getPersons(PersonType personType) {
        return persons.stream().filter(p -> p.getType() == personType).collect(Collectors.toList());
    }

    public List<Person> getPerson(String name) {
        return persons.stream().filter(p -> p.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public Optional<Person> getPersonById(Integer id) {
        log.info("Finding person by id {}" , id);

        Optional<Person> match = persons.stream()
                .filter((person) -> person.getId() == id)
                .reduce((u, v) -> {
                    throw new IllegalStateException("More than one ID found");
                });
        return match;
//        List<Person> personList = persons.stream()
//                .filter(p -> p.getId() == id)
//                .collect(Collectors.toList());
//        return Optional.of(personList.get(0));
    }

    public boolean addPerson(Person newPerson) {
        if (getPersons(newPerson) == null) {
            return persons.add(newPerson);
        } else {
            return false;
        }
    }
}
