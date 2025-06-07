package org.fsierra.spring.cursos.models.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cursos")
@Schema(description = "Entidad que representa un curso disponible en la plataforma.")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del curso", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;


    @NotBlank(message = "El título no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Título del curso", example = "Introducción a Java")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(nullable = false)
    @Schema(description = "Descripción del curso", example = "Curso básico de programación en Java")
    private String descripcion;

    @NotBlank(message = "El instructor no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Nombre del instructor del curso", example = "Fernando Sierra")
    private String instructor;

    @NotNull(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Indica si el curso está activo", example = "true")
    private Boolean estado;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha en que se registró el curso", example = "2025-05-01", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDate.now();



    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
