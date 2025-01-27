package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;
    @MockBean
    private AccidentTypeService accidentTypeService;
    @MockBean
    private RuleService ruleService;

    @Test
    @WithMockUser
    @Transactional
    void whenGetAllThenAccidentsList() throws Exception {
        when(accidentService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/accidents"))
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/list"))
                .andExpect(model().attributeExists("accidents"));
    }

    @Test
    @WithMockUser
    void whenGetCreationPageThenAccidentsCreate() throws Exception {
        when(accidentTypeService.findALl()).thenReturn(List.of());
        when(ruleService.findAll()).thenReturn(Set.of());
        mockMvc.perform(get("/accidents/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/create"))
                .andExpect(model().attributeExists("types", "rules"));
    }

    @Test
    @WithMockUser
    void whenGetFormUpdateAccidentThenAccidentsEdit() throws Exception {
        AccidentType accidentType = new AccidentType(1, "test");
        Rule rule = new Rule(1, "test");
        Accident accident = new Accident(1, "test", "test", "test", accidentType, Set.of(rule));

        when(accidentService.findById(any(Integer.class))).thenReturn(Optional.of(accident));
        mockMvc.perform(get("/accidents/formUpdateAccident").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/edit"))
                .andExpect(model().attributeExists("accident", "types", "rules"));
    }

    @Test
    @WithMockUser
    void whenGetFormUpdateAccidentThenError() throws Exception {
        mockMvc.perform(get("/accidents/formUpdateAccident").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    @WithMockUser
    void whenGetFormUpdateAccidentThenErrors404() throws Exception {
        mockMvc.perform(get("/accidents/formUpdateAccident").param("id", "50"))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }

    @Test
    @WithMockUser
    void whenPostCreateOrUpdateThenAccidents() throws Exception {
        AccidentType accidentType = new AccidentType(1, "testType");
        Rule rule = new Rule(1, "testRule");
        Accident accident = new Accident(
                0, "testN", "testT", "testA", accidentType, Set.of(rule));

        when(accidentService.save(any(Accident.class), eq(List.of(rule.getId())))).thenReturn(Optional.of(accident));
        mockMvc.perform(post("/accidents/create")
                        .param("name", accident.getName())
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rulesId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accidents"));

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<List<Integer>> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(accidentService).save(accidentCaptor.capture(), listCaptor.capture());
        var captorValue = accidentCaptor.getValue();
        assertThat(captorValue.getName()).isEqualTo("testN");
        assertThat(captorValue.getAddress()).isEqualTo("testA");
        assertThat(captorValue.getType().getId()).isEqualTo(1);
        assertThat(listCaptor.getValue()).isEqualTo(List.of(1));
    }

    @Test
    @WithMockUser
    void whenPostCreateOrUpdateThenError() throws Exception {
        AccidentType accidentType = new AccidentType(1, "test");
        Rule rule = new Rule(1, "test");
        Accident accident = new Accident(0, "test", "test", "test", accidentType, Set.of(rule));

        mockMvc.perform(post("/accidents/create")
                        .param("name", accident.getName())
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rulesId", String.valueOf(accident.getRules().iterator().next().getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("message"));
    }
}