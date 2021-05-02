package com.safetynet.safenynetalerts.controller;

import com.jsoniter.output.JsonStream;
import com.safetynet.safenynetalerts.model.Person;
import com.safetynet.safenynetalerts.service.Child;
import com.safetynet.safenynetalerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Read - Get all persons
     *
     * @return - An Iterable object of Person full filled
     */
    @GetMapping("/person")
    public Iterable<Person> getPersons() {
        log.info("Received request GET /person");
        Iterable<Person> result = personService.getPersons();
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }


    /**
     * Read - Get one person
     */
    @GetMapping("/person/{fname}/{lname}")
    public Person getPersonByName(@PathVariable String fname, @PathVariable String lname) {
        log.info("Received request GET /person/ with path variables {} {}", fname, lname);
        Person result = personService.getPerson(fname, lname);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Create - Add one person
     */
    @PostMapping("/person")
    public Person createPerson(@RequestBody Person person) {
        log.info("Received request POST /person");
        Person result = personService.createPerson(person);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Update - Update one person
     */
    @PutMapping("/person/{fname}/{lname}")
    public Person updatePersonByName(@PathVariable String fname, @PathVariable String lname, @RequestBody Person person) {
        log.info("Received request PUT /person/ with path variables {} {}", fname, lname);
        Person result = personService.updatePerson(fname, lname, person);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Delete - Delete one person
     */
    @DeleteMapping("/person/{fname}/{lname}")
    public void deletePersonByName(@PathVariable String fname, @PathVariable String lname) {
        log.info("Received request DELETE /person/ with path variables {} {}", fname, lname);
        personService.deletePerson(fname, lname);
    }

}
