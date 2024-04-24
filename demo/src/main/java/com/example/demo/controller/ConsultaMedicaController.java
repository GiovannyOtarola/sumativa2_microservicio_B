package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

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

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;


import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/ConsultasMedicas")
public class ConsultaMedicaController {
    
    private static final Logger log = LoggerFactory.getLogger(ConsultaMedicaController.class);
    
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
    public CollectionModel<EntityModel<ConsultaMedica>> getAllConsultasMedicas() {
        List<ConsultaMedica> consultasMedicas = consultaMedicaService.getAllConsultasMedicas();
        log.info("GET /ConsultasMedicas");
        log.info("Retornando todas las ConsultasMedicas");
        List<EntityModel<ConsultaMedica>> consultaMedicaResources = consultasMedicas.stream()
            .map( consultaMedica -> EntityModel.of(consultaMedica,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getConsultaMedicaById(consultaMedica.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllConsultasMedicas());
        CollectionModel<EntityModel<ConsultaMedica>> resources = CollectionModel.of(consultaMedicaResources, linkTo.withRel("consultaMedica"));

        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<ConsultaMedica> getConsultaMedicaById(@PathVariable Long id) {
        Optional<ConsultaMedica> consultaMedica = consultaMedicaService.getConsultaMedicaById(id);

        if (consultaMedica.isPresent()) {
            return EntityModel.of(consultaMedica.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getConsultaMedicaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllConsultasMedicas()).withRel("all-consultasMedicas"));
        } else {
            throw new NotFoundException("Consulta Medica not found with id: " + id);
        }
    }

    @PostMapping
    public EntityModel<ConsultaMedica> createConsultaMedica(@Validated @RequestBody ConsultaMedica consultaMedica) {
        ConsultaMedica createdConsultaMedica = consultaMedicaService.createConsultaMedica(consultaMedica);
            return EntityModel.of(createdConsultaMedica,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getConsultaMedicaById(createdConsultaMedica.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllConsultasMedicas()).withRel("all-consultasMedicas"));

    }

    @PutMapping("/{id}")
    public EntityModel<ConsultaMedica> updateConsultaMedica(@PathVariable Long id, @RequestBody ConsultaMedica consultaMedica) {
        ConsultaMedica updatedConsultaMedica = consultaMedicaService.updateConsultaMedica(id, consultaMedica);
        return EntityModel.of(updatedConsultaMedica,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getConsultaMedicaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllConsultasMedicas()).withRel("all-consultasMedicas"));

    }

    @DeleteMapping("/{id}")
    public void deleteConsultaMedica(@PathVariable Long id){
        consultaMedicaService.deleteConsultaMedica(id);
    }
}
