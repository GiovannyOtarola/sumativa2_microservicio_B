package com.example.demo.controller;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;
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

    @Test
    public void obtenerTodosTest() throws Exception{
        ConsultaMedica consultaMedica1 = new ConsultaMedica();
        consultaMedica1.setMotivoConsulta("Dolor cabeza");
        consultaMedica1.setDiagnostico("le duele la cabeza");
        consultaMedica1.setFecha(LocalDate.of(2024, 4, 11));
        consultaMedica1.setPacienteid(1L);
        consultaMedica1.setId(1L);

        ConsultaMedica consultaMedica2 = new ConsultaMedica();
        consultaMedica2.setMotivoConsulta("Dolor espalda");
        consultaMedica2.setDiagnostico("le duele la espalda");
        consultaMedica2.setFecha(LocalDate.of(2024, 4, 28));
        consultaMedica2.setPacienteid(2L);
        consultaMedica2.setId(2L);

        List<ConsultaMedica> consultasMedicas =  Arrays.asList(consultaMedica1,consultaMedica2);
        
        when(consultaMedicaServiceMock.getAllConsultasMedicas()).thenReturn(consultasMedicas);

        mockMvc.perform(MockMvcRequestBuilders.get("/ConsultasMedicas"))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList", Matchers.hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[0].motivoConsulta", Matchers.is("Dolor cabeza")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[0].diagnostico", Matchers.is("le duele la cabeza")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[0].fecha", Matchers.is("2024-04-11")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[0].pacienteid", Matchers.is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[0]._links.self.href", Matchers.is("http://localhost/ConsultasMedicas/1")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[1].motivoConsulta", Matchers.is("Dolor espalda")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[1].diagnostico", Matchers.is("le duele la espalda")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[1].fecha", Matchers.is("2024-04-28")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[1].pacienteid", Matchers.is(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.consultaMedicaList[1]._links.self.href", Matchers.is("http://localhost/ConsultasMedicas/2")));
    }

    @Test
    public void deleteConsultaMedicaTest() throws Exception {
        // ID del usuario a eliminar
        Long consultaMedicaId = 1L;

        // Simular la llamada al servicio deleteUsuario
        doNothing().when(consultaMedicaServiceMock).deleteConsultaMedica(consultaMedicaId);

        // Realizar la solicitud DELETE al endpoint /Usuarios/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/ConsultasMedicas/{id}", consultaMedicaId))
            .andExpect(MockMvcResultMatchers.status().isOk()); // Verificar el código de estado

        // Verificar que el método deleteUsuario del servicio se llamó con el ID correcto
        verify(consultaMedicaServiceMock, times(1)).deleteConsultaMedica(consultaMedicaId);
    }

}
