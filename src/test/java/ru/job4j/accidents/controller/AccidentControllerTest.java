package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @Transactional
    void whenGetAllThenAccidentsList() throws Exception {
        mockMvc.perform(get("/accidents"))
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/list"));
    }

    @Test
    @WithMockUser
    void whenGetCreationPageThenAccidentsCreate() throws Exception {
        mockMvc.perform(get("/accidents/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/create"));
    }

    @Test
    @WithMockUser
    void whenGetFormUpdateAccidentThenAccidentsEdit() throws Exception {
        mockMvc.perform(get("/accidents/formUpdateAccident").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/edit"))
                .andExpect(model().attributeExists("accident", "types", "rules"));
    }

    @Test
    @WithMockUser
    void whenGetFormUpdateAccidentThenErrors404() throws Exception {
        mockMvc.perform(get("/accidents/formUpdateAccident").param("id", "50"))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }
}