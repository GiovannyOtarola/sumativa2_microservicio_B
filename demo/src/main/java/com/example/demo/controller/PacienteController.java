package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

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
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;


import java.util.stream.Collectors;


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
    public CollectionModel<EntityModel<Paciente>> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        log.info("GET /pacientes");
        log.info("Retornando todos los pacientes");
        List<EntityModel<Paciente>> pacientesResources = pacientes.stream()
            .map( paciente -> EntityModel.of(paciente,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(paciente.getId())).withSelfRel()
                
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes());
        CollectionModel<EntityModel<Paciente>> resources = CollectionModel.of(pacientesResources, linkTo.withRel("pacientes"));

        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<Paciente> getPacienteById(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);

        if (paciente.isPresent()) {
            return EntityModel.of(paciente.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCantidadConsultasMedicas(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes()).withRel("all-pacientes"));
        } else {
            throw new NotFoundException("Paciente not found with id: " + id);
        }
    }
    
    @GetMapping("/{id}/consultasMedicas/cantidad")
    public ResponseEntity<Integer> getCantidadConsultasMedicas(@PathVariable Long id) {
        int cantidadConsultas = pacienteService.getCantidadConsultasMedicas(id);
         return ResponseEntity.ok().body(cantidadConsultas);
    }


    @PostMapping
    public EntityModel<Paciente> createPaciente(@Validated @RequestBody Paciente paciente) {
        Paciente createdPaciente = pacienteService.createPaciente(paciente);
            return EntityModel.of(createdPaciente,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(createdPaciente.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCantidadConsultasMedicas(createdPaciente.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes()).withRel("all-pacientes"));

    }

    @PutMapping("/{id}")
    public EntityModel<Paciente> updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        Paciente updatedPaciente = pacienteService.updatePaciente(id, paciente);
        return EntityModel.of(updatedPaciente,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCantidadConsultasMedicas(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes()).withRel("all-pacientes"));

    }

    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable Long id){
        pacienteService.deletePaciente(id);
    }


}
