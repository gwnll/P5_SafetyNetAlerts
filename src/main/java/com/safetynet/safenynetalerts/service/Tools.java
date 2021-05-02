package com.safetynet.safenynetalerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Tools {

    public static Integer computeAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears();
    }
}
