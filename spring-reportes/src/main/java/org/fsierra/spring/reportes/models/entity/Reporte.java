package org.fsierra.spring.reportes.models.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Entidad que representa un reporte generado por un usuario")
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del reporte", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "El id del usuario no puede ser nulo")
    @Schema(description = "Identificador del usuario que genera el reporte", example = "5")
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @NotNull(message = "El tipo de reporte no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo de reporte (RESUMEN o DETALLADO)", example = "RESUMEN")
    @Column(name = "tipo_reporte", nullable = false)
    private TipoReporte tipoReporte;

    @NotNull(message = "La fecha de pago asociada no puede ser nula")
    @Schema(description = "Fecha asociada al pago que motivó el reporte", example = "2025-06-01")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Schema(description = "Fecha en que se generó el reporte", example = "2025-06-02")
    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;

    @PrePersist
    public void prePersist() {
        this.fechaGeneracion = LocalDate.now();
    }

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

    public TipoReporte getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporte tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
