package org.fsierra.spring.inscripciones.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
@Schema(description = "Entidad que representa la inscripción de un estudiante a un curso.")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion", nullable = false, updatable = false)
    @Schema(description = "ID único de la inscripción", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "El id del usuario no puede estar vacío")
    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del usuario que se inscribe", example = "101")
    private Long idUsuario;

    @NotNull(message = "El id del curso no puede estar vacío")
    @Column(name = "id_curso", nullable = false)
    @Schema(description = "ID del curso al que se inscribe el usuario", example = "202")
    private Long idCurso;

    @Column(name = "fecha_inscripcion")
    @Schema(description = "Fecha en la que se realizó la inscripción", example = "2025-05-01", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate fechaInscripcion;

    @NotNull(message = "El estado no puede estar vacío")
    @Schema(description = "Estado de la inscripción", example = "true")
    private Boolean estado;

    @PrePersist
    public void prePersist() {
        this.fechaInscripcion = LocalDate.now();
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}