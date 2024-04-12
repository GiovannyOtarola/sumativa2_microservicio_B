package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    
}
