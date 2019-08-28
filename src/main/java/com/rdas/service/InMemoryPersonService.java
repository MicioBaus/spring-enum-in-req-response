package com.rdas.service;

import com.rdas.model.Person;
import com.rdas.model.PersonType;
import com.rdas.repo.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class InMemoryPersonService {

    private final PersonRepository personRepository;

    @Autowired
    public InMemoryPersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void init() {
        log.info("Init data");

        personRepository.save( Person.builder().name("Rana").age(40).type(PersonType.MALE).build() );
        personRepository.save( Person.builder().name("Jennifer").age(40).type(PersonType.FEMALE).build() );
        personRepository.save( Person.builder().name("Connal").age(100).type(PersonType.GENDER_NEUTRAL).build() );
        personRepository.save( Person.builder().name("Florence").age(99).type(PersonType.FEMALE).build() );
        personRepository.save( Person.builder().name("Zack").age(11).type(PersonType.MALE).build() );
    }

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public List<Person> getPersons(Person personToSearch) {

        List persons = new ArrayList<>();
        Optional<Person>  person = personRepository.findById(personToSearch.getId());

        if (person.isPresent()) {
            persons.add(person);
        }

        return persons;

    }

    public List<Person> getPersons(PersonType personType) {
        return personRepository.findByType(personType);
    }

    public List<Person> getPerson(String name) {
        return personRepository.findByName(name);
    }

    public Optional<Person> getPersonById(Integer id) {
        log.info("Finding person by id {}" , id);

        return personRepository.findById(id);
    }

    public boolean addPerson(Person newPerson) {
        if (getPersons(newPerson) == null) {
            personRepository.save(newPerson);
            return true;
        } else {
            return false;
        }
    }
}
