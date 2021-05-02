package com.safetynet.safenynetalerts.repository;

import com.safetynet.safenynetalerts.model.MedicalRecord;
import com.safetynet.safenynetalerts.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class MedicalRecordRepository {

    private final DataHolder dataHolder;

    public MedicalRecordRepository(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return dataHolder.getData().getMedicalrecords();
    }

    public MedicalRecord findMedicalRecordByName(String fname, String lname) {
        return findAllMedicalRecords().stream()
                .filter(p->p.getFirstName().equals(fname))
                .filter(p->p.getLastName().equals(lname))
                .findFirst().orElse(null);
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalrecord) {
        List<MedicalRecord> medicalRecords = findAllMedicalRecords();
        medicalRecords.add(medicalrecord);
        dataHolder.save();
        return medicalrecord;
    }

    public MedicalRecord updateMedicalRecord(String fname, String lname, MedicalRecord medicalrecord) {
        MedicalRecord toUpdate = findMedicalRecordByName(fname, lname);
        toUpdate.setBirthdate(medicalrecord.getBirthdate());
        toUpdate.setMedications(medicalrecord.getMedications());
        toUpdate.setAllergies(medicalrecord.getAllergies());
        dataHolder.save();
        return toUpdate;
    }

    public void deleteMedicalRecord(String fname, String lname) {
        List<MedicalRecord> medicalRecords = findAllMedicalRecords().stream()
                .filter(p->!p.getFirstName().equals(fname))
                .filter(p->!p.getLastName().equals(lname))
                .collect(toList());
        dataHolder.getData().setMedicalrecords(medicalRecords);
        dataHolder.save();
    }


}
