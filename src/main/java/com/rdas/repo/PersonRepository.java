package com.rdas.repo;

import com.rdas.model.Person;
import com.rdas.model.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findById(int id);

    List<Person> findByName(String name);

    List<Person> findAll();

    List<Person> findByType(PersonType type);
    
    <S extends Person> S save(S entity);
}