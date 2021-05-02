package com.safetynet.safenynetalerts.repository;

import com.safetynet.safenynetalerts.model.Firestation;
import com.safetynet.safenynetalerts.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class FirestationRepository {

    private final DataHolder dataHolder;

    public FirestationRepository(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    public List<Firestation> findAllStations() {
        return dataHolder.getData().getFirestations();
    }

    public Firestation findFirestationByAddress(String address) {
        return findAllStations() .stream()
                .filter(p->p.getAddress().equals(address))
                .findFirst().orElse(null);
    }

    public Firestation saveFirestation(Firestation firestation) {
        List<Firestation> firestations = findAllStations();
        firestations.add(firestation);
        dataHolder.save();
        return firestation;
    }

    public Firestation updateFirestationByAddress(String address, Firestation firestation) {
        Firestation toUpdate = findFirestationByAddress(address);
        toUpdate.setStation(firestation.getStation());
        dataHolder.save();
        return toUpdate;
    }

    public void deletePerson(String address) {
        List<Firestation> firestations = findAllStations().stream()
                .filter(p->!p.getAddress().equals(address))
                .collect(toList());
        dataHolder.getData().setFirestations(firestations);
        dataHolder.save();
    }

    public List<String> findAllAddressesByStationNumber(String stationNumber) {
        return findAllStations().stream()
                .filter(p->p.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(toList());
    }

}
