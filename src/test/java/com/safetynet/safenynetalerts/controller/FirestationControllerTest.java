package com.safetynet.safenynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safenynetalerts.TestUtils;
import com.safetynet.safenynetalerts.model.Firestation;
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
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void getFirestationsTest() throws Exception {

        String expected = "[{\"address\":\"1509 Culver St\",\"station\":\"3\"},{\"address\":\"29 15th St\",\"station\":\"2\"},{\"address\":\"834 Binoc Ave\",\"station\":\"3\"},{\"address\":\"644 Gershwin Cir\",\"station\":\"1\"},{\"address\":\"748 Townings Dr\",\"station\":\"3\"},{\"address\":\"112 Steppes Pl\",\"station\":\"3\"},{\"address\":\"489 Manchester St\",\"station\":\"4\"},{\"address\":\"892 Downing Ct\",\"station\":\"2\"},{\"address\":\"908 73rd St\",\"station\":\"1\"},{\"address\":\"112 Steppes Pl\",\"station\":\"4\"},{\"address\":\"947 E. Rose Dr\",\"station\":\"1\"},{\"address\":\"748 Townings Dr\",\"station\":\"3\"},{\"address\":\"951 LoneTree Rd\",\"station\":\"2\"}]";

        String response = mockMvc.perform(get("/firestation")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);
    }

    @Test
    public void getFirestationTest() throws Exception {

        String expected = "{\"address\":\"1509 Culver St\",\"station\":\"3\"}";

        String response = mockMvc.perform(get("/firestation/{address}", "1509 Culver St"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void createFirestationTest() throws Exception {
        Firestation station = new Firestation();
        station.setStation("15");
        station.setAddress("test address");

        String stationString = objectMapper.writeValueAsString(station);

        String response = mockMvc.perform(post("/firestation")
                .content(objectMapper.writeValueAsString(station))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(stationString);

        testUtils.reload();

    }

    @Test
    public void updateFirestationTest() throws Exception {

        Firestation updatedStation = new Firestation();
        updatedStation.setStation("3");
        updatedStation.setAddress("1509 Culver St");
        String updatedStationString = objectMapper.writeValueAsString(updatedStation);

        String response = mockMvc.perform(put("/firestation/{address}", "1509 Culver St")
                .content(objectMapper.writeValueAsString(updatedStation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(updatedStationString);

        testUtils.reload();

    }

    @Test
    public void deleteFirestationTest() throws Exception {

        String response = mockMvc.perform(delete("/firestation/{address}", "test address"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo("");

        testUtils.reload();

    }

}
