package com.safetynet.safenynetalerts;

import com.jsoniter.JsonIterator;
import com.safetynet.safenynetalerts.model.Data;
import com.safetynet.safenynetalerts.repository.DataHolder;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class TestUtils {

    private final DataHolder dataholder;
    private final ResourceLoader resourceLoader;

    public TestUtils(DataHolder dataholder, ResourceLoader resourceLoader) {
        this.dataholder = dataholder;
        this.resourceLoader = resourceLoader;
    }

    public void reload() {
        String test = new String("");
        try {
            test = FileUtils.readFileToString(resourceLoader.getResource("classpath:data-backup.json").getFile(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Data data = JsonIterator.deserialize(test, Data.class);
        dataholder.getData().setMedicalrecords(data.getMedicalrecords());
        dataholder.getData().setFirestations(data.getFirestations());
        dataholder.getData().setPersons(data.getPersons());
        dataholder.save();
    }

}
