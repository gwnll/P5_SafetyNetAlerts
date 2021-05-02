package com.safetynet.safenynetalerts.model;

import java.util.List;

@lombok.Data
public class Data {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;
}
