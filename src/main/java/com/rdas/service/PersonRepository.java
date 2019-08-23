package com.rdas.service;

import com.rdas.model.Person;
import com.rdas.model.PersonType;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findById(int id);

    List<Person> findByName(String name);

    List<Person> findAll();

    List<Person> findByType(PersonType type);
    
    <S extends Person> S save(S entity);
}