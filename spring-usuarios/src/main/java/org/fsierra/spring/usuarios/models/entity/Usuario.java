package org.fsierra.spring.usuarios.models.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@Schema(description = "Entidad que representa un usuario de la plataforma")
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre completo del usuario", example = "Catalina Pizarro")
    private String nombre;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe ser un correo válido")
    @Column(unique = true)
    @Schema(description = "Correo electrónico único del usuario", example = "catalina@example.com")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Schema(description = "Contraseña del usuario", example = "123456", writeOnly = true)
    @Column(nullable = false)
    private String password;

    @NotNull(message = "El estado no puede ser nulo")
    @Schema(description = "Estado del usuario (activo/inactivo)", example = "true")
    private Boolean estado;

    @Schema(description = "Fecha de registro del usuario", example = "2025-04-27")
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @NotNull(message = "El rol no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Rol del usuario (ADMINISTRADOR, INSTRUCTOR o ESTUDIANTE)", example = "ESTUDIANTE")
    private Rol rol;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }



}