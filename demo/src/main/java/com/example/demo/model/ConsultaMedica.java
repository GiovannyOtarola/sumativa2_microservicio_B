package com.example.demo.model;

import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "consulta_medica")
public class ConsultaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull(message = "motivo obligatorio")
    @NotBlank(message = "No puede ingresar un motivo vacio")
    @Column(name = "motivoConsulta")
    private String motivoConsulta;

    @NotNull(message = "diagnostico obligatorio")
    @NotBlank(message = "No puede ingresar un diagnostico vacio")
    @Column(name = "diagnostico")
    private String diagnostico;

    
    @NotNull(message = "fecha obligatoria")
    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "paciente_id")
    private Long pacienteid;
}
