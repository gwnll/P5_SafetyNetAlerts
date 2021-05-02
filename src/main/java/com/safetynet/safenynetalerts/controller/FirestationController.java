package com.safetynet.safenynetalerts.controller;

import com.jsoniter.output.JsonStream;
import com.safetynet.safenynetalerts.model.Firestation;
import com.safetynet.safenynetalerts.service.FirestationService;
import com.safetynet.safenynetalerts.service.PeopleCoveredByStationNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class FirestationController {

    private FirestationService firestationService;

    public FirestationController(FirestationService firestationService ) {
        this.firestationService = firestationService;
    }

    /**
     * Read - Get all stations
     * @return - An Iterable object of Firestation full filled
     */
    @GetMapping("/firestation")
    public Iterable<Firestation> getFirestations() {
        log.info("Received request GET /firestation");
        Iterable<Firestation> result = firestationService.getFirestations();
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Read - Get one station
     */
    @GetMapping("/firestation/{address}")
    public Firestation getFirestationByAddress(@PathVariable String address) {
        log.info("Received request GET /firestation with path variables {}", address);
        Firestation result = firestationService.getStation(address);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Create - Add a station
     */
    @PostMapping("/firestation")
    public Firestation createFirestation(@RequestBody Firestation firestation) {
        log.info("Received request POST /firestation");
        Firestation result = firestationService.createFirestation(firestation);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Update - Update a station
     */
    @PutMapping("/firestation/{address}")
    public Firestation updateFirestationByAddress(@PathVariable String address, @RequestBody Firestation firestation) {
        log.info("Received request PUT /firestation with path variables {}", address);
        Firestation result = firestationService.updateFirestation(address, firestation);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Delete - Delete a station
     */
    @DeleteMapping("/firestation/{address}")
    public void deleteFirestationByAddress(@PathVariable String address) {
        log.info("Received request DELETE /firestation with path variables {}", address);
        firestationService.deleteStation(address);
    }

}
