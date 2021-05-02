package com.safetynet.safenynetalerts.controller;

import com.jsoniter.output.JsonStream;
import com.safetynet.safenynetalerts.model.MedicalRecord;
import com.safetynet.safenynetalerts.model.Person;
import com.safetynet.safenynetalerts.service.MedicalRecordService;
import com.safetynet.safenynetalerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Read - Get all medical records
     * @return - An Iterable object of MedicalRecord full filled
     */
    @GetMapping("/medicalrecord")
    public Iterable<MedicalRecord> getMedicalRecords() {
        log.info("Received request GET /medicalRecord");
        Iterable<MedicalRecord> result =  medicalRecordService.getMedicalRecords();
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Read - Get one medical record
     */
    @GetMapping("/medicalrecord/{fname}/{lname}")
    public MedicalRecord getMedicalRecordByName(@PathVariable String fname, @PathVariable String lname) {
        log.info("Received request GET /medicalRecord/ with path variables {} {}", fname, lname);
        MedicalRecord result = medicalRecordService.getMedicalRecord(fname, lname);
        log.info("Response: {}", result != null ? JsonStream.serialize(result) : "null");
        return result;
    }

    /**
     * Create - Add one medical record
     */
    @PostMapping("/medicalrecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalrecord) {
        log.info("Received request POST /medicalRecord/");
        MedicalRecord result = medicalRecordService.createMedicalRecord(medicalrecord);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Update - Update one medical record
     */
    @PutMapping("/medicalrecord/{fname}/{lname}")
    public MedicalRecord updateMedicalRecordByName(@PathVariable String fname, @PathVariable String lname, @RequestBody MedicalRecord medicalrecord) {
        log.info("Received request PUT /medicalRecord/ with path variables {} {}", fname, lname);
        MedicalRecord result = medicalRecordService.updateMedicalRecord(fname, lname, medicalrecord);
        log.info("Response: {}", JsonStream.serialize(result));
        return result;
    }

    /**
     * Delete - Delete one medical record
     */
    @DeleteMapping("/medicalrecord/{fname}/{lname}")
    public void deleteMedicalRecordByName(@PathVariable String fname, @PathVariable String lname) {
        log.info("Received request DELETE /medicalRecord/ with path variables {} {}", fname, lname);
        medicalRecordService.deleteMedicalRecord(fname, lname);
    }
}
