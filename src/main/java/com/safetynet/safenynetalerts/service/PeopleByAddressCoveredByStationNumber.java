package com.safetynet.safenynetalerts.service;

import com.safetynet.safenynetalerts.model.MedicalRecord;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeopleByAddressCoveredByStationNumber {

    private String address;
    private List<Person> people;

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
