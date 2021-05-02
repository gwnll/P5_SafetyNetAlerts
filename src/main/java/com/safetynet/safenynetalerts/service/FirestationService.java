package com.safetynet.safenynetalerts.service;

import com.safetynet.safenynetalerts.model.Firestation;
import com.safetynet.safenynetalerts.model.Person;
import com.safetynet.safenynetalerts.repository.FirestationRepository;
import com.safetynet.safenynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safenynetalerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Iterable<Firestation> getFirestations() {
        return firestationRepository.findAllStations();
    }

    public Firestation getStation(String address) {
        return firestationRepository.findFirestationByAddress(address);
    }

    public Firestation createFirestation(Firestation firestation) {
        return firestationRepository.saveFirestation(firestation);
    }

    public Firestation updateFirestation(String address, Firestation firestation) {
        return firestationRepository.updateFirestationByAddress(address, firestation);
    }

    public void deleteStation(String address) {
        firestationRepository.deletePerson(address);
    }

    public PeopleCoveredByStationNumber getPeopleByStationNumber(String stationNumber) {
        List<String> addresses = firestationRepository.findAllAddressesByStationNumber(stationNumber);
        List<PeopleCoveredByStationNumber.Person> people = addresses.stream().flatMap(a -> personRepository.findAllPeopleByAddress(a).stream())
                .map(p -> PeopleCoveredByStationNumber.Person.builder()
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .address(p.getAddress())
                        .phone(p.getPhone())
                        .build()
                )
                .collect(Collectors.toList());
        long adultsNumber = people.stream().map(p -> medicalRecordRepository.findMedicalRecordByName(p.getFirstName(), p.getLastName()))
                .filter(m -> Tools.computeAge(m.getBirthdate()) >= 18)
                .count();
        long childrenNumber = people.stream().map(p -> medicalRecordRepository.findMedicalRecordByName(p.getFirstName(), p.getLastName()))
                .filter(m -> Tools.computeAge(m.getBirthdate()) < 18)
                .count();
        return PeopleCoveredByStationNumber.builder().adultsNumber(adultsNumber).childrenNumber(childrenNumber).people(people).build();
    }

    public List<String> getPhoneNumbersByStationNumber(String stationNumber) {
        List<String> addresses = firestationRepository.findAllAddressesByStationNumber(stationNumber);
        List<String> phoneNumbers = new ArrayList<>();
        for (String address : addresses) {
            List<Person> people = personRepository.findAllPeopleByAddress(address);
            for (Person person : people) {
                phoneNumbers.add(person.getPhone());
            }
        }
        return phoneNumbers;

    }

}
