package com.example.demo.service;

import com.example.demo.model.*;
import java.util.List;
import java.util.Optional;

public interface ConsultaMedicaService {

    List<ConsultaMedica> getAllConsultasMedicas();
    Optional<ConsultaMedica> getConsultaMedicaById(Long id);
    ConsultaMedica createConsultaMedica(ConsultaMedica consultaMedica);
    ConsultaMedica updateConsultaMedica(Long id, ConsultaMedica consultaMedica);
    void deleteConsultaMedica(Long id);
    
}
