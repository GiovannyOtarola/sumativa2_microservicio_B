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
import com.example.demo.service.ConsultaMedicaService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/ConsultasMedicas")
public class ConsultaMedicaController {
    
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
    private ConsultaMedicaService consultaMedicaService;

    @GetMapping
    public List<ConsultaMedica> getAllConsultaMedicas(){
        log.info("GET /Consultas Medicas");
        log.info("Retonando todas las Consultas Medicas");
        return consultaMedicaService.getAllConsultasMedicas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getConsultaMedicaById(@PathVariable Long id){
        Optional<ConsultaMedica> consultaMedica = consultaMedicaService.getConsultaMedicaById(id);
        
        if (consultaMedica.isEmpty()) {
            log.error("No se encontro Consulta Medica con ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontró Consulta Medica de ID " + id));
        }
        return ResponseEntity.ok(consultaMedica);
    }

    @PostMapping
    public ResponseEntity<Object> createConsultaMedica(@Validated @RequestBody ConsultaMedica consultaMedica){
        ConsultaMedica createdConsultaMedica = consultaMedicaService.createConsultaMedica(consultaMedica);
        if (createdConsultaMedica == null) {
            log.error("Error al crear la Consulta Medica {}", consultaMedica);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear la Consulta Medica"));
        }
        return ResponseEntity.ok(createdConsultaMedica);
    }

    @PutMapping("/{id}")
    public ConsultaMedica updateConsultaMedica(@PathVariable Long id, @RequestBody ConsultaMedica consultaMedica){
        return consultaMedicaService.updateConsultaMedica(id, consultaMedica);
    }

    @DeleteMapping("/{id}")
    public void deleteConsultaMedica(@PathVariable Long id){
        consultaMedicaService.deleteConsultaMedica(id);
    }
}
