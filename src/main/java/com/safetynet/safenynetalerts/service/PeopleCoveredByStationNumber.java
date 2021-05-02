package com.safetynet.safenynetalerts.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeopleCoveredByStationNumber {

    public PeopleCoveredByStationNumber(long adultsNumber, long childrenNumber, List<Person> people) {
        this.adultsNumber = adultsNumber;
        this.childrenNumber = childrenNumber;
        this.people = people;
    }

    private long adultsNumber;
    private long childrenNumber;
    private List<Person> people;

    @Data
    @Builder
    public static class Person {

        public Person(String firstName, String lastName, String address, String phone) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.phone = phone;
        }

        private String firstName;
        private String lastName;
        private String address;
        private String phone;
    }
}
