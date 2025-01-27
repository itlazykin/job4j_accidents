package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.model.*;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void whenGetLoginPageThenLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void whenGetRegPageThenReg() throws Exception {
        mockMvc.perform(get("/reg"))
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    void whenGetLogoutPageThenLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }

    @Test
    void whenPostRegSaveThenGetLoginPage() throws Exception {
        User user = new User(0, "testN", "testP", true, new Authority());
        when(userService.save(any(User.class))).thenReturn(Optional.of(user));
        mockMvc.perform(post("/reg")
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).save(userCaptor.capture());
        var captorValue = userCaptor.getValue();
        assertThat(captorValue.getUsername()).isEqualTo("testN");
        assertThat(captorValue.getPassword()).isEqualTo("testP");
    }

    @Test
    void whenPostRegSaveThenGetRegPage() throws Exception {
        User user = new User(0, "testN", "testP", true, new Authority());
        when(userService.save(any(User.class))).thenReturn(Optional.empty());
        mockMvc.perform(post("/reg")
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("reg"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}