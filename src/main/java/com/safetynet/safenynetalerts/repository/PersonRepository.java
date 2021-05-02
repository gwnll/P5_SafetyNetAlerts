package com.safetynet.safenynetalerts.repository;

import com.safetynet.safenynetalerts.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PersonRepository {

    private final DataHolder dataHolder;

    public PersonRepository(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    public List<Person> findAllPeople() {
        return dataHolder.getData().getPersons();
    }

    public List<String> findAllEmailsByCity(String city) {
        return findAllPeople().stream()
                .filter(p->p.getCity().equals(city))
                .map(Person::getEmail)
                .collect(toList());
    }

    public Person findPersonByName(String fname, String lname) {
        return findAllPeople().stream()
                .filter(p->p.getFirstName().equals(fname))
                .filter(p->p.getLastName().equals(lname))
                .findFirst().orElse(null);
    }

    public List<Person> findPeopleByName(String fname, String lname) {
        return findAllPeople().stream()
                .filter(p->p.getFirstName().equals(fname))
                .filter(p->p.getLastName().equals(lname))
                .collect(toList());
    }

    public Person savePerson(Person person) {
        List<Person> people = findAllPeople();
        people.add(person);
        dataHolder.save();
        return person;
    }

    public Person updatePerson(String fname, String lname, Person person) {
        Person toUpdate = findPersonByName(fname, lname);
        toUpdate.setFirstName(person.getFirstName());
        toUpdate.setLastName(person.getLastName());
        toUpdate.setAddress(person.getAddress());
        toUpdate.setZip(person.getZip());
        toUpdate.setCity(person.getCity());
        toUpdate.setEmail(person.getEmail());
        toUpdate.setPhone(person.getPhone());
        dataHolder.save();
        return toUpdate;
    }

    public void deletePerson(String fname, String lname) {
        List<Person> people = findAllPeople().stream()
                .filter(p->!p.getFirstName().equals(fname))
                .filter(p->!p.getLastName().equals(lname))
                .collect(toList());
        dataHolder.getData().setPersons(people);
        dataHolder.save();
    }

    public List<Person> findAllPeopleByAddress(String address) {
        return findAllPeople().stream()
                .filter(p->p.getAddress().equals(address))
                .collect(toList());
    }


}
