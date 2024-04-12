package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.*;
import com.example.demo.service.PacienteService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/Pacientes")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);
    
    static class ErrorResponse {
        private final String message;
    
        public ErrorResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> getAllPacientes(){
        log.info("GET /pacientes");
        log.info("Retonando todas los pacientes");
        return pacienteService.getAllPacientes();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteById(@PathVariable Long id){
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);
        
        if (paciente.isEmpty()) {
            log.error("No se encontro paciente con ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontró al paciente de ID " + id));
        }
        return ResponseEntity.ok(paciente);
    }
    @GetMapping("/{id}/consultasMedicas/cantidad")
    public ResponseEntity<Integer> getCantidadConsultasMedicas(@PathVariable Long id) {
        int cantidadConsultas = pacienteService.getCantidadConsultasMedicas(id);
        return ResponseEntity.ok(cantidadConsultas);
    }


    @PostMapping
    public ResponseEntity<Object> createPaciente(@Validated @RequestBody Paciente paciente){
        Paciente createdPaciente = pacienteService.createPaciente(paciente);
        if (createdPaciente == null) {
            log.error("Error al crear al paciente {}", paciente);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear al paciente"));
        }
        return ResponseEntity.ok(createdPaciente);
    }

    @PutMapping("/{id}")
    public Paciente updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente){
        return pacienteService.updatePaciente(id, paciente);
    }

    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable Long id){
        pacienteService.deletePaciente(id);
    }


}
