package com.example.demo.service;

import com.example.demo.model.*;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    
    List<Paciente> getAllPacientes();
    Optional<Paciente> getPacienteById(Long id);
    Paciente createPaciente(Paciente paciente);
    Paciente updatePaciente(Long id, Paciente paciente);
    void deletePaciente(Long id);
}
