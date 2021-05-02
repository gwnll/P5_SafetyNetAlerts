package com.safetynet.safenynetalerts.service;

import com.safetynet.safenynetalerts.model.Firestation;
import com.safetynet.safenynetalerts.model.MedicalRecord;
import com.safetynet.safenynetalerts.model.Person;
import com.safetynet.safenynetalerts.repository.FirestationRepository;
import com.safetynet.safenynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safenynetalerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final FirestationRepository firestationRepository;
    private Person person;

    public PersonService(PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, FirestationRepository firestationRepository) {
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.firestationRepository = firestationRepository;
    }

    public Iterable<Person> getPersons() {
        return personRepository.findAllPeople();
    }

    public Person getPerson(String fname, String lname) {
        return personRepository.findPersonByName(fname, lname);
    }

    public Person createPerson(Person person) {
        return personRepository.savePerson(person);
    }

    public Person updatePerson(String fname, String lname, Person person) {
        return personRepository.updatePerson(fname, lname, person);
    }

    public void deletePerson(String fname, String lname) {
        personRepository.deletePerson(fname, lname);
    }

    public List<String> findAllEmailsByCity(String city) {
        return personRepository.findAllEmailsByCity(city);
    }

    public List<Child> getChildrenByAddress(String address) {
        List<Person> people = personRepository.findAllPeopleByAddress(address);
        List<Child> children = new ArrayList<>();
        for (Person p : people) {
            List<Child.Other> others = people.stream().filter(x -> !x.getFirstName().equals(p.getFirstName()))
                    .map(x -> Child.Other.builder()
                            .firstName(x.getFirstName())
                            .lastName(x.getLastName())
                            .build())
                    .collect(Collectors.toList());
            MedicalRecord medicalRecordByName = medicalRecordRepository.findMedicalRecordByName(p.getFirstName(), p.getLastName());
            int age = Tools.computeAge(medicalRecordByName.getBirthdate());
            if (age < 18) {
                Child build = Child.builder()
                        .firstName(medicalRecordByName.getFirstName())
                        .lastName(medicalRecordByName.getLastName())
                        .age(age)
                        .others(others)
                        .build();
                children.add(build);
            }
        }

        return children;

    }

    public PeopleAndStationNumberCoveredByAddress getPeopleAndFireStationNumberByAddress(String address) {
        List<Person> persons = personRepository.findAllPeopleByAddress(address);
        List<PeopleAndStationNumberCoveredByAddress.Person> people = new ArrayList<>();
        String stationNumber = firestationRepository.findFirestationByAddress(address).getStation();
        for (Person p : persons) {
            MedicalRecord medicalRecordByName = medicalRecordRepository.findMedicalRecordByName(p.getFirstName(), p.getLastName());
            int age = Tools.computeAge(medicalRecordByName.getBirthdate());
            people = persons.stream().filter(x -> !x.getFirstName().equals(p.getFirstName()))
                    .map(x -> PeopleAndStationNumberCoveredByAddress.Person.builder()
                            .firstName(x.getFirstName())
                            .lastName(x.getLastName())
                            .phone(x.getPhone())
                            .age(age)
                            .medications(medicalRecordByName.getMedications())
                            .allergies(medicalRecordByName.getAllergies())
                            .build())
                    .collect(Collectors.toList());
        }
        return PeopleAndStationNumberCoveredByAddress.builder().stationNumber(stationNumber).people(people).build();
    }

    public List<PeopleByAddressCoveredByStationNumber> getPeopleByAddressCoveredByStationNumber(List<String> stations) {
        List<PeopleByAddressCoveredByStationNumber> homes = new ArrayList<>();
        for (String stationNumber : stations) {
            List<String> addresses = firestationRepository.findAllAddressesByStationNumber(stationNumber);
            for (String address : addresses) {
                List<Person> persons = personRepository.findAllPeopleByAddress(address);
                List<PeopleByAddressCoveredByStationNumber.Person> people = new ArrayList<>();
                for (Person p : persons) {
                    MedicalRecord medicalRecordByName = medicalRecordRepository.findMedicalRecordByName(p.getFirstName(), p.getLastName());
                    int age = Tools.computeAge(medicalRecordByName.getBirthdate());
                    people.add(PeopleByAddressCoveredByStationNumber.Person.builder()
                            .firstName(p.getFirstName())
                            .lastName(p.getLastName())
                            .phone(p.getPhone())
                            .age(age)
                            .medications(medicalRecordByName.getMedications())
                            .allergies(medicalRecordByName.getAllergies())
                            .build());
                }
                PeopleByAddressCoveredByStationNumber home = PeopleByAddressCoveredByStationNumber.builder().address(address).people(people).build();
                homes.add(home);
            }
        }
        return homes;
    }

    public List<PersonAndItsMedicalRecord> getPeopleAndTheirMedicalRecord(String fname, String lname) {
        List<PersonAndItsMedicalRecord> people = new ArrayList<>();
        List<Person> persons = personRepository.findPeopleByName(fname, lname);
        for (Person person : persons) {
            MedicalRecord medicalRecordByName = medicalRecordRepository.findMedicalRecordByName(person.getFirstName(), person.getLastName());
            int age = Tools.computeAge(medicalRecordByName.getBirthdate());

            PersonAndItsMedicalRecord build = new PersonAndItsMedicalRecord();
                    build.setFirstName(person.getFirstName());
                    build.setLastName(person.getLastName());
                    build.setAddress(person.getAddress());
                    build.setAge(age);
                    build.setEmail((person.getEmail()));
                    build.setMedications(medicalRecordByName.getMedications());
                    build.setAllergies(medicalRecordByName.getAllergies());
            people.add(build);
        }
        return people;
    }
}
