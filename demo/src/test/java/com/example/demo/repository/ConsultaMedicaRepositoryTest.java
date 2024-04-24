package com.example.demo.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.model.ConsultaMedica;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConsultaMedicaRepositoryTest {

    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    @Test
    public void guardarConsultaMedicaTest(){
        ConsultaMedica consultaMedica = new ConsultaMedica();
        consultaMedica.setMotivoConsulta("Dolor cabeza");
        consultaMedica.setDiagnostico("le duele la cabeza");
        consultaMedica.setFecha(LocalDate.of(2024, 4, 11));
        consultaMedica.setPacienteid(1L);
        consultaMedica.setId(1L);

        ConsultaMedica resultado = consultaMedicaRepository.save(consultaMedica);

        assertNotNull(resultado.getId());
        assertEquals("Dolor cabeza", resultado.getMotivoConsulta());
        assertEquals("le duele la cabeza", resultado.getDiagnostico());
        assertEquals(LocalDate.of(2024, 4, 11), resultado.getFecha());
        assertEquals(1L, resultado.getPacienteid());
        assertEquals(1L, resultado.getId());

    }
    
}
