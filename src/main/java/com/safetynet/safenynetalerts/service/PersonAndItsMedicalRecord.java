package com.safetynet.safenynetalerts.service;

import lombok.Data;

import java.util.List;

@Data
public class PersonAndItsMedicalRecord {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medications;
    private List<String> allergies;
}
