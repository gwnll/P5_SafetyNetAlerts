package com.safetynet.safenynetalerts.service;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class Child {
    private String firstName;
    private String lastName;
    private int age;
    private List<Other> others;

    @Data
    @Builder
    public static class Other {
        private String firstName;
        private String lastName;
    }
}