package com.example.demo.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
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

    @Test
    public void obtenerConsultaMedicaPorIdTest(){

       //ConsultaMedica 1 guardado en la base de datos
       //motivoConsulta: "Fiebre y tos"
       //Diagnostico: "Gripe común"
       //Fecha: 11/04/2024
       //PacienteId: 1

        ConsultaMedica resultado = consultaMedicaRepository.findById(1L).get();

        assertNotNull(resultado.getId());
        assertEquals("Fiebre y tos", resultado.getMotivoConsulta());
        assertEquals("Gripe común", resultado.getDiagnostico());
        assertEquals(LocalDate.of(2024, 4, 11), resultado.getFecha());
        assertEquals(1L, resultado.getPacienteid());
        assertEquals(1L, resultado.getId());

    }
    
    //verificar si el metodo findById funciona correctamente
    @Test
    public void buscarConsultaMedicaPorIdTest(){
        ConsultaMedica consultaMedica = consultaMedicaRepository.findById(1L).get();

        Assertions.assertThat(consultaMedica.getId()).isEqualTo(1L);
    }

     //verifica si el metodo findAll funciona correctamente y que la base de datos no esta vacia
     @Test
     public void findAllConsultasMedicasTest(){
 
         List<ConsultaMedica> consultasMedicas =  consultaMedicaRepository.findAll();
 
         Assertions.assertThat(consultasMedicas.size()).isGreaterThan(0);
     }
}
