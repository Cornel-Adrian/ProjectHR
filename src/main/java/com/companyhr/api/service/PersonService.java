package com.companyhr.api.service;

import com.companyhr.api.model.Person;

import java.util.List;

public interface PersonService {

    Person createPerson(Person person);

    Person getPerson(Long id);

    Person editPerson(Person person);

    void deletePerson(Person person);

    void deletePerson(Long id);

    List<Person> getAllPersons(int pageNumber, int pageSiz);

    List<Person> getAllPersons();
}
