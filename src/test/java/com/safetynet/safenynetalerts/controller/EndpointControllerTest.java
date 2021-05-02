package com.safetynet.safenynetalerts.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EndpointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PeopleByStationNumberTest() throws Exception {

        String expected = "{\"adultsNumber\":5,\"childrenNumber\":1,\"people\":[{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"address\":\"644 Gershwin Cir\",\"phone\":\"841-874-6512\"},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"phone\":\"841-874-8547\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\",\"phone\":\"841-874-7462\"},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"}]}";

        String response = mockMvc.perform(get("/firestationnumber")
                .param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void ChildrenByAddressTest() throws Exception {

        String expected = "[{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"age\":9,\"others\":[{\"firstName\":\"John\",\"lastName\":\"Boyd\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\"},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\"},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\"}]},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"age\":3,\"others\":[{\"firstName\":\"John\",\"lastName\":\"Boyd\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\"},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\"},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\"}]}]";

        String response = mockMvc.perform(get("/childAlert")
                .param("address", "1509 Culver St")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void PhoneNumbersByStationNumberTest() throws Exception {

        String expected = "[\"841-874-6513\",\"841-874-7878\",\"841-874-7512\",\"841-874-7512\",\"841-874-7458\"]";

        String response = mockMvc.perform(get("/phoneAlert")
                .param("firestation", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void PeopleAndFireStationNumberByAddressTest() throws Exception {

        String expected = "{\"people\":[{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7878\",\"age\":4,\"medications\":[],\"allergies\":[]},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7512\",\"age\":4,\"medications\":[],\"allergies\":[]}],\"stationNumber\":\"2\"}";

        String response = mockMvc.perform(get("/fire")
                .param("address", "892 Downing Ct")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void PeopleByAddressCoveredByStationNumberTest() throws Exception {

        String expected = "[{\"address\":\"29 15th St\",\"people\":[{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"phone\":\"841-874-6513\",\"age\":32,\"medications\":[],\"allergies\":[]}]},{\"address\":\"892 Downing Ct\",\"people\":[{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7878\",\"age\":33,\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7512\",\"age\":36,\"medications\":[],\"allergies\":[]},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7512\",\"age\":4,\"medications\":[],\"allergies\":[]}]},{\"address\":\"951 LoneTree Rd\",\"people\":[{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"phone\":\"841-874-7458\",\"age\":75,\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]}]";

        String response = mockMvc.perform(get("/flood/stations")
                .param("stations", "2,5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void PeopleAndTheirMedicalRecordTest() throws Exception {

        String expected = "[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"age\":37,\"email\":\"jaboyd@email.com\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]}]";

        String response = mockMvc.perform(get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Boyd")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

    @Test
    public void AllEmailsByCityTest() throws Exception {

        String expected = "[\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"tcoop@ymail.com\",\"lily@email.com\",\"soph@email.com\",\"ward@email.com\",\"zarc@email.com\",\"reg@email.com\",\"jpeter@email.com\",\"jpeter@email.com\",\"aly@imail.com\",\"bstel@email.com\",\"ssanw@email.com\",\"bstel@email.com\",\"clivfd@ymail.com\",\"gramps@email.com\"]";

        String response = mockMvc.perform(get("/communityEmail")
                .param("city", "Culver")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(expected);

    }

}
