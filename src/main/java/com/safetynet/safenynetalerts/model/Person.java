package com.safetynet.safenynetalerts.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
public class Person {

    @JsonPropertyOrder("1")
    private String firstName;
    @JsonPropertyOrder("2")
    private String lastName;
    @JsonPropertyOrder("3")
    private String address;
    @JsonPropertyOrder("4")
    private String city;
    @JsonPropertyOrder("5")
    private String zip;
    @JsonPropertyOrder("6")
    private String phone;
    @JsonPropertyOrder("7")
    private String email;

    public Person() {

    }

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}
