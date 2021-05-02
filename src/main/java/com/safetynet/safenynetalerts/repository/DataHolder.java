package com.safetynet.safenynetalerts.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.safetynet.safenynetalerts.model.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@Component
public class DataHolder {

    private final Data data;
    private final ResourceLoader resourceLoader;

    public DataHolder(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        data = this.parse();
    }

    private Data parse() {
        String test = new String("");
        try {
            test = FileUtils.readFileToString(resourceLoader.getResource("classpath:data.json").getFile(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonIterator.deserialize(test, Data.class);
    }

    public Data getData() {
        return this.data;
    }

    public void save() {
        String data = JsonStream.serialize(this.data);
        try {
            Files.write(resourceLoader.getResource("classpath:data.json").getFile().toPath(), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
