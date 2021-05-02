package com.safetynet.safenynetalerts.controller;

import com.jsoniter.output.JsonStream;
import com.safetynet.safenynetalerts.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class EndpointController {

    private FirestationService firestationService;
    private final PersonService personService;

    public EndpointController(FirestationService firestationService, PersonService personService) {
        this.firestationService = firestationService;
        this.personService = personService;
    }

    /**
     * Read - Get People by Station Number
     */
    @GetMapping("/firestationnumber")
    public PeopleCoveredByStationNumber getPeopleByStationNumber(@RequestParam String stationNumber) {
        log.info("Received request GET /firestationnumber with parameter {}", stationNumber);
        PeopleCoveredByStationNumber result = firestationService.getPeopleByStationNumber(stationNumber);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }


    /**
     * Read - Get children by address
     *
     */
    @GetMapping("/childAlert")
    public List<Child> getChildrenByAddress(@RequestParam String address) {
        log.info("Received request GET /childAlert with parameter {}", address);
        List<Child> result = personService.getChildrenByAddress(address);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Read - Get Phone Numbers by Station Number
     */
    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumbersByStationNumber(@RequestParam String firestation) {
        log.info("Received request GET /phoneAlert with parameter {}", firestation);
        List<String> result = firestationService.getPhoneNumbersByStationNumber(firestation);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Read - Get People and Firestation Number by address
     */
    @GetMapping("/fire")
    public PeopleAndStationNumberCoveredByAddress getPeopleAndFireStationNumberByAddress(@RequestParam String address) {
        log.info("Received request GET /fire with parameter {}", address);
        PeopleAndStationNumberCoveredByAddress result = personService.getPeopleAndFireStationNumberByAddress(address);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Read - Get People (grouped by address) by Firestation
     */
    @GetMapping("/flood/stations")
    public List<PeopleByAddressCoveredByStationNumber> getPeopleByAddressCoveredByStationNumber(@RequestParam List<String> stations) {
        log.info("Received request GET /flood/stations with parameter {}", stations);
        List<PeopleByAddressCoveredByStationNumber> result = personService.getPeopleByAddressCoveredByStationNumber(stations);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Read - Get person and its medical record
     */
    @GetMapping("/personInfo")
    public List<PersonAndItsMedicalRecord> getPeopleAndTheirMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("Received request GET /personInfo with parameter {} {}", firstName, lastName);
        List<PersonAndItsMedicalRecord> result = personService.getPeopleAndTheirMedicalRecord(firstName, lastName);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Read - Get all emails
     */
    @GetMapping("/communityEmail")
    public List<String> getAllEmailsByCity(@RequestParam String city) {
        log.info("Received request GET /communityEmail with parameter {}", city);
        List<String> result = personService.findAllEmailsByCity(city);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

}
