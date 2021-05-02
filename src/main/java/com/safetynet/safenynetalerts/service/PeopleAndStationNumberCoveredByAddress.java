package com.safetynet.safenynetalerts.service;

import com.safetynet.safenynetalerts.model.MedicalRecord;
import com.safetynet.safenynetalerts.repository.MedicalRecordRepository;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeopleAndStationNumberCoveredByAddress {

    private List<Person> people;
    private String stationNumber;

    @Data
    @Builder
    public static class Person {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;
    }
}
