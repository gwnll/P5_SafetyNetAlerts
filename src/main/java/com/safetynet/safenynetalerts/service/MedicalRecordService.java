package com.safetynet.safenynetalerts.service;

import com.safetynet.safenynetalerts.model.MedicalRecord;
import com.safetynet.safenynetalerts.model.Person;
import com.safetynet.safenynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safenynetalerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAllMedicalRecords();
    }

    public MedicalRecord getMedicalRecord(String fname, String lname) {
        return medicalRecordRepository.findMedicalRecordByName(fname, lname);
    }

    public MedicalRecord createMedicalRecord(MedicalRecord medicalrecord) {
        return medicalRecordRepository.saveMedicalRecord(medicalrecord);
    }

    public MedicalRecord updateMedicalRecord(String fname, String lname, MedicalRecord medicalrecord) {
        return medicalRecordRepository.updateMedicalRecord(fname, lname, medicalrecord);
    }

    public void deleteMedicalRecord(String fname, String lname) {
        medicalRecordRepository.deleteMedicalRecord(fname, lname);
    }
}
