package pl.jakubdudek.springboot.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jakubdudek.springboot.repository.GroupRepository;
import pl.jakubdudek.springboot.repository.RateRepository;
import pl.jakubdudek.springboot.entity.Group;
import pl.jakubdudek.springboot.entity.Rate;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RateControllerTest {
    @Mock
    private RateRepository rateRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private RateController rateController;

    private MockMvc mockMvc;

    public RateControllerTest() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(rateController).build();
    }

    @Test
    public void testCreateRateIsOk() throws Exception {
        Rate rate = new Rate(6, "Komentarz 423", 1L);

        Group group = new Group();
        when(groupRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(group));
        when(rateRepository.save(any(Rate.class))).thenReturn(new Rate());

        Gson gson = new Gson();
        String json = gson.toJson(rate);

        mockMvc.perform(post("/api/rating/")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()
        );

        verify(groupRepository).findById(1L);
        verify(rateRepository).save(any(Rate.class));
    }

    @Test
    public void testCreateRateNotFound() throws Exception {
        Rate rate = new Rate(6, "Komentarz 423", 1L);

        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        Gson gson = new Gson();
        String json = gson.toJson(rate);

        mockMvc.perform(post("/api/rating/")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound()
        );

        verify(groupRepository).findById(1L);
        verify(rateRepository, never()).save(any(Rate.class));
    }
}