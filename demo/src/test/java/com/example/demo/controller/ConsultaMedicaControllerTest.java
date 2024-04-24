package com.example.demo.controller;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.ConsultaMedica;
import com.example.demo.service.ConsultaMedicaServiceImpl;


@WebMvcTest(ConsultaMedicaController.class)
public class ConsultaMedicaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultaMedicaServiceImpl consultaMedicaServiceMock;

    @Test
    public void obtenerConsultaMedicaPorIdTest() throws Exception {
        ConsultaMedica consultaMedica = new ConsultaMedica();
        consultaMedica.setMotivoConsulta("Dolor cabeza");
        consultaMedica.setDiagnostico("le duele la cabeza");
        consultaMedica.setFecha(LocalDate.of(2024, 4, 11));
        consultaMedica.setPacienteid(1L);
        consultaMedica.setId(1L);

    when(consultaMedicaServiceMock.getConsultaMedicaById(1L)).thenReturn(Optional.of(consultaMedica));

    mockMvc.perform(MockMvcRequestBuilders.get("/ConsultasMedicas/1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.motivoConsulta", Matchers.is("Dolor cabeza")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.diagnostico", Matchers.is("le duele la cabeza")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.fecha", Matchers.is("2024-04-11")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pacienteid", Matchers.is(1)));
    }

}
