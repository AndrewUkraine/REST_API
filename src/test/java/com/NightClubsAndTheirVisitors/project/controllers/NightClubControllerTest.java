package com.NightClubsAndTheirVisitors.project.controllers;

import com.NightClubsAndTheirVisitors.project.entities.NightClub;
import com.NightClubsAndTheirVisitors.project.services.NightClubService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = NightClubController.class)
@WebAppConfiguration
@WebMvcTest
class NightClubControllerTest {

    private static final String REST_URL = "/night-club" + "/";

    @MockBean
    private NightClubService nightClubService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RequestBuilder requestBuilder;

    @Test
    void createNightClub() {
    }

    @Test
    void editNightClub() {
    }

    @Test
    void onVisit() {
    }

    @Test
    void getNightClubById() throws Exception {
        NightClub newNightClub = new NightClub("alex");
        nightClubService.saveOrUpdateNightClub(newNightClub);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + newNightClub.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }

}