package com.example.demo.model;

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
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "rut obligatorio")
    @NotBlank(message = "No puede ingresar un rut vacio")
    @Column(name = "rut")
    private String rut;

    @NotNull(message = "nombre obligatorio")
    @NotBlank(message = "No puede ingresar un nombre vacio")
    @Column(name = "nombre")
    private String nombre;

    @NotNull(message = "apellido obligatorio")
    @NotBlank(message = "No puede ingresar un apellido vacio")
    @Column(name = "apellido")
    private String apellido;

    @NotNull(message = "direccion obligatoria")
    @NotBlank(message = "No puede ingresar una direccion vacia")
    @Column(name = "direccion")
    private String direccion;
}
