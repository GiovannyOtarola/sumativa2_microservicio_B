package com.example.demo.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.ConsultaMedica;
import com.example.demo.repository.ConsultaMedicaRepository;

@ExtendWith(MockitoExtension.class)
public class ConsultaMedicaServiceTest {
    
    @InjectMocks
    private ConsultaMedicaServiceImpl consultaMedicaService;

    @Mock
    private ConsultaMedicaRepository consultaMedicaRepositoryMock;

    @Test
    public void guardarUsuarioTest(){
        ConsultaMedica consultaMedica = new ConsultaMedica();
        consultaMedica.setMotivoConsulta("Dolor cabeza");
        consultaMedica.setDiagnostico("le duele la cabeza");
        consultaMedica.setFecha(LocalDate.of(2024, 4, 11));
        consultaMedica.setPacienteid(1L);
        consultaMedica.setId(1L);

        when(consultaMedicaRepositoryMock.save(any())).thenReturn(consultaMedica);

        ConsultaMedica resultado = consultaMedicaRepositoryMock.save(consultaMedica);

        
        assertEquals("Dolor cabeza", resultado.getMotivoConsulta());
        assertEquals("le duele la cabeza", resultado.getDiagnostico());
        assertEquals(LocalDate.of(2024, 4, 11), resultado.getFecha());
        assertEquals(1L, resultado.getPacienteid());
        assertEquals(1L, resultado.getId());

    }
    
}

