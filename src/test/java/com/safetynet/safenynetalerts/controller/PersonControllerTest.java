package com.safetynet.safenynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.output.JsonStream;
import com.safetynet.safenynetalerts.TestUtils;
import com.safetynet.safenynetalerts.model.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void getPersonsTest() throws Exception {

        mockMvc.perform(get("/person")
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    public void getPersonByNameTest() throws Exception {

        String expected = "{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"address\":\"951 LoneTree Rd\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7458\",\"email\":\"gramps@email.com\"}";

        String response = mockMvc.perform(get("/person/{fname}/{lname}", "Eric", "Cadigan"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void createPersonTest() throws Exception {

        Person trucBidule = new Person("Truc", "Bidule", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "bstel@email.com");
        String trucBiduleString = objectMapper.writeValueAsString(trucBidule);

        String response = mockMvc.perform(post("/person")
                .content(objectMapper.writeValueAsString(trucBidule))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(trucBiduleString);

        testUtils.reload();

    }

    @Test
    public void updatePersonTest() throws Exception {

        Person updatedPerson = new Person("NewRon", "NewPeters", "112 Steppes Pl", "Culver", "97451", "841-874-8888", "jpeter@email.com");
        String updatedPersonString = objectMapper.writeValueAsString(updatedPerson);

        String response = mockMvc.perform(put("/person/{fname}/{lname}", "Ron", "Peters")
                .content(objectMapper.writeValueAsString(updatedPerson))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(updatedPersonString);

        testUtils.reload();

    }

    @Test
    public void deletePersonTest() throws Exception {

        String response1 = mockMvc.perform(delete("/person/{fname}/{lname}", "Jonanathan", "Marrack"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        testUtils.reload();

    }

}
